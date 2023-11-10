package com.example.jeliBankBackend.context;
import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountStatusRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferToAccountRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDepositeDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseGetDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.repository.AccountRepository;
import com.example.jeliBankBackend.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AcountServiceTests {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;


    // CREATE TESTS
    @Test
    public void testCreateAccount() throws ResourseNotFoundException {
        // Arrange
        AccountRequestDto accountRequestDto = new AccountRequestDto("John Doe", 1000.0);

        when(accountRepository.getAccountByAccountNumber(Mockito.anyInt())).thenReturn(Optional.empty());

        // Act
        AccountResponseDto response = accountService.createAccount(accountRequestDto);

        // Asserts
        assertEquals("John Doe", response.getOwnerName());
        assertEquals(1000.0, response.getInitialAmount());
    }
    @Test
    public void testCreateAccount_DuplicateAccountNumber() {
        // Arrange
        AccountRequestDto accountRequestDto = new AccountRequestDto("John Doe", 1000.0);

        when(accountRepository.getAccountByAccountNumber(Mockito.anyInt())).thenReturn(Optional.of(new Account()));

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountService.createAccount(accountRequestDto));
    }

    @Test
    public void testCreateAccount_DataAccessException() {
        // Arrange
        AccountRequestDto accountRequestDto = new AccountRequestDto("John Doe", 1000.0);

        // act
        when(accountRepository.getAccountByAccountNumber(Mockito.anyInt())).thenReturn(Optional.empty());
        when(accountRepository.save(Mockito.any())).thenThrow(new DataAccessException("Simulated database error") {});

        // assert
        assertThrows(ResourseNotFoundException.class, () -> accountService.createAccount(accountRequestDto));
    }

    // DEPOSIT TESTS

    @Test
    public void testDepositIntoAccount_SuccessfulDeposit() throws ResourseNotFoundException {
        // Arrange
        int accountNumber = 123456;
        double initialBalance = 1000.0;
        double amountToDeposit = 500.0;
        Account account = new Account(accountNumber, "John Doe", initialBalance);
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act
        AccountResponseDepositeDto result = accountService.depositIntoAccount(accountNumber, amountToDeposit);

        // Assert
        assertEquals(initialBalance + amountToDeposit, result.getAmount(), 0.001);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDepositIntoAccount_AccountNotFound() {
        // Arrange
        int accountNumber = 123456;
        double amountToDeposit = 500.0;
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () ->
                accountService.depositIntoAccount(accountNumber, amountToDeposit));
    }
    @Test
    public void testDepositIntoAccount_NegativeAmount() {
        // Arrange
        int accountNumber = 123456;
        double amountToDeposit = -500.0;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () ->
                accountService.depositIntoAccount(accountNumber, amountToDeposit));
    }
    @Test
    public void testDepositIntoAccount_DataAccessException() {
        // Arrange
        int accountNumber = 123456;
        double amountToDeposit = 500.0;
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(new Account()));
        doThrow(new DataAccessException("Simulated data access exception") {}).when(accountRepository).save(any());

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () ->
                accountService.depositIntoAccount(accountNumber, amountToDeposit));
    }

    // TRANSFER BEETWEEN ACCOUNTS
    @Test
    public void testTransferBetweenAccounts_SuccessfulTransfer() throws ResourseNotFoundException {
        // Arrange
        int sourceAccountNumber = 123456;
        int destinationAccountNumber = 789012;
        double initialSourceBalance = 1000.0;
        double initialDestinationBalance = 500.0;
        double transferAmount = 200.0;

        Account sourceAccount = new Account(sourceAccountNumber, "John Doe", initialSourceBalance);
        Account destinationAccount = new Account(destinationAccountNumber, "Jane Doe", initialDestinationBalance);

        when(accountRepository.getAccountByAccountNumber(sourceAccountNumber)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.getAccountByAccountNumber(destinationAccountNumber)).thenReturn(Optional.of(destinationAccount));

        // Act
        AccountTransferToAccountRequestDto result = accountService.transferBetweenAccounts(
                new AccountTransferToAccountRequestDto(sourceAccountNumber, destinationAccountNumber, transferAmount));

        // Assert
        assertEquals(sourceAccountNumber, result.getSourceAccountNumber());
        assertEquals(destinationAccountNumber, result.getDestinationAccountNumber());
        assertEquals(transferAmount, result.getAmount(), 0.001);
        assertEquals(initialSourceBalance - transferAmount, sourceAccount.getBalance(), 0.001);
        assertEquals(initialDestinationBalance + transferAmount, destinationAccount.getBalance(), 0.001);
        verify(accountRepository, times(2)).save(any());
    }
    @Test
    public void testTransferBetweenAccounts_InsufficientFunds() {
        // Arrange
        int sourceAccountNumber = 123456;
        int destinationAccountNumber = 789012;
        double initialSourceBalance = 100.0;
        double initialDestinationBalance = 500.0;
        double transferAmount = 200.0;

        Account sourceAccount = new Account(sourceAccountNumber, "John Doe", initialSourceBalance);
        Account destinationAccount = new Account(destinationAccountNumber, "Jane Doe", initialDestinationBalance);

        when(accountRepository.getAccountByAccountNumber(sourceAccountNumber)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.getAccountByAccountNumber(destinationAccountNumber)).thenReturn(Optional.of(destinationAccount));

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () ->
                accountService.transferBetweenAccounts(
                        new AccountTransferToAccountRequestDto(sourceAccountNumber, destinationAccountNumber, transferAmount)));
    }

    // GET ACCOUNT DETAILS

    @Test
    public void testGetAccountDetails_SuccessfulQuery() throws ResourseNotFoundException {
        // Arrange
        int accountNumber = 123456;
        double initialBalance = 1000.0;

        Account account = new Account(accountNumber, "John Doe", initialBalance);
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act
        Optional<AccountResponseGetDto> result = accountService.getAccountDetails(accountNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(accountNumber, result.get().getAccountNumber());
        assertEquals("John Doe", result.get().getOwnerName());
        assertEquals(initialBalance, result.get().getBalance(), 0.001);
    }
    @Test
    public void testGetAccountDetails_AccountNotFound() throws ResourseNotFoundException {
        // Arrange
        int accountNumber = 123456;
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act
        Optional<AccountResponseGetDto> result = accountService.getAccountDetails(accountNumber);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testToggleAccountStatus_AccountExists_ActiveToInactive() throws ResourseNotFoundException {
        // Arrange
        Account existingAccount = new Account();
        existingAccount.setIsActive(true);

        AccountStatusRequestDto requestDto = new AccountStatusRequestDto();
        requestDto.setAccountNumber(123);  // Replace with a valid account number

        when(accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber())).thenReturn(Optional.of(existingAccount));

        // Act
        String result = accountService.toggleAccountStatus(requestDto);

        // Assert
        assertTrue(result.contains("Cuenta desactivada"));
        assertFalse(existingAccount.getIsActive());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void testToggleAccountStatus_AccountExists_InactiveToActive() throws ResourseNotFoundException {
        // Arrange
        Account existingAccount = new Account();
        existingAccount.setIsActive(false);

        AccountStatusRequestDto requestDto = new AccountStatusRequestDto();
        requestDto.setAccountNumber(456);  // Replace with a valid account number

        when(accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber())).thenReturn(Optional.of(existingAccount));

        // Act
        String result = accountService.toggleAccountStatus(requestDto);

        // Assert
        assertTrue(result.contains("Cuenta activada"));
        assertTrue(existingAccount.getIsActive());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void testToggleAccountStatus_AccountDoesNotExist() {
        // Arrange
        AccountStatusRequestDto requestDto = new AccountStatusRequestDto();
        requestDto.setAccountNumber(789);  // Replace with an invalid account number

        when(accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountService.toggleAccountStatus(requestDto));
        verify(accountRepository, never()).save(any());
    }
// OTROS MÉTODOS

    @Test
    public void testAccountRequestDtoToEntity_Conversion() {
        // Arrange
        AccountRequestDto requestDto = new AccountRequestDto("John Doe", 1000.0);

        // Act
        Account result = accountService.AccountRequestDtotoEntity(requestDto);

        // Assert
        assertEquals(requestDto.getOwnerName(), result.getOwnerName());
        assertEquals(requestDto.getBalance(), result.getBalance(), 0.001);
    }

    @Test
    public void testGenerateAccountNumber_RandomNumber() {
        // Arrange & Act
        int result = accountService.generateAccountNumber();

        // Assert
        assertTrue(result >= 1000000000 && result <= Integer.MAX_VALUE);
    }

}
