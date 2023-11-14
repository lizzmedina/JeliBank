package com.example.jeliBankBackend.context.service;

import com.example.jeliBankBackend.dtos.requests.account.*;
import com.example.jeliBankBackend.dtos.responses.account.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.repository.AccountRepository;
import com.example.jeliBankBackend.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AcountServiceTests {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AcountServiceTests.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        assertEquals(1000.0, response.getBalance());
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
        AccountDepositeRequestDto amount = new AccountDepositeRequestDto(amountToDeposit);
        AccountDepositeResponseDto result = accountService.depositIntoAccount(accountNumber, amount);

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
        AccountDepositeRequestDto amount = new AccountDepositeRequestDto(amountToDeposit);

        AccountDepositeResponseDto result = new AccountDepositeResponseDto(amountToDeposit);
        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () ->
                accountService.depositIntoAccount(accountNumber, amount));
    }

    @Test
    public void testDepositIntoAccount_NegativeAmount()  {
        // Arrange
        int accountNumber = 123456;
        double amountToDeposit = -500.0;
        AccountDepositeRequestDto amount = new AccountDepositeRequestDto(amountToDeposit);
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () ->
                accountService.depositIntoAccount(accountNumber, amount));
    }

    @Test
    public void testDepositIntoAccount_DataAccessException() {
        // Arrange
        int accountNumber = 123456;
        double amountToDeposit = 500.0;
        AccountDepositeRequestDto amount = new AccountDepositeRequestDto(amountToDeposit);
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(new Account()));
        doThrow(new DataAccessException("Simulated data access exception") {}).when(accountRepository).save(any());

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () ->
                accountService.depositIntoAccount(accountNumber, amount));
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
        AccountTransferRequestDto transferRequest = new AccountTransferRequestDto(sourceAccountNumber, destinationAccountNumber, transferAmount);
        AccountTransferResponseDto result = accountService.transferBetweenAccounts(transferRequest);

        // Assert
        assertEquals(sourceAccountNumber, result.getSourceAccountNumber());
        assertEquals(destinationAccountNumber, result.getDestinationAccountNumber());
        assertEquals(transferAmount, result.getAmount(), 0.001);


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
                        new AccountTransferRequestDto(sourceAccountNumber, destinationAccountNumber, transferAmount)));
    }

    // GET ACCOUNT DETAILS

    @Test
    public void testGetAccountDetails_SuccessfulQuery() throws ResourseNotFoundException {
        // Arrange
        int accountNumber = 123456;
        double initialBalance = 1000.0;
        AccountGetResponseDto response = new AccountGetResponseDto(accountNumber, "John Doe", initialBalance);

        Account account = new Account(accountNumber, "John Doe", initialBalance);
        when(accountRepository.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act
        Optional<AccountGetResponseDto> result = accountService.getAccountDetails(new AccountGetRequestDto(accountNumber));

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
        Optional<AccountGetResponseDto> result = accountService.getAccountDetails(new AccountGetRequestDto(accountNumber));

        // Assert
        assertFalse(result.isPresent());
    }


    @Test
    void testToggleAccountStatus_AccountExists_ActiveToInactive() throws ResourseNotFoundException {
        // Arrange
        Account existingAccount = new Account();
        existingAccount.setIsActive(true);

        AccountBlockRequestDto requestDto = new AccountBlockRequestDto(existingAccount.getAccountNumber());
        requestDto.setAccountNumber(123);  // Replace with a valid account number

        when(accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber())).thenReturn(Optional.of(existingAccount));

        // Act
        AccountBlockResponseDto result = accountService.toggleAccountStatus(requestDto);

        // Assert
        assertEquals(result.getMessage(), "Cuenta desactivada");
        assertFalse(existingAccount.getIsActive());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void testToggleAccountStatus_AccountExists_InactiveToActive() throws ResourseNotFoundException {
        // Arrange
        Account existingAccount = new Account();
        existingAccount.setIsActive(false);

        AccountBlockRequestDto requestDto = new AccountBlockRequestDto(existingAccount.getAccountNumber());
        requestDto.setAccountNumber(456);  // Replace with a valid account number

        when(accountRepository.getAccountByAccountNumber(requestDto.getAccountNumber())).thenReturn(Optional.of(existingAccount));

        // Act
        AccountBlockResponseDto result = accountService.toggleAccountStatus(requestDto);

        // Assert
        assertEquals(result.getMessage(), "Cuenta activada");
        assertTrue(existingAccount.getIsActive());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void testToggleAccountStatus_AccountDoesNotExist() {
        // Arrange
        int nonExistentAccountNumber = 789;
        AccountBlockRequestDto requestDto = new AccountBlockRequestDto(nonExistentAccountNumber);

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
        assertEquals( result.getBalance(), requestDto.getInitialAmount());
    }

    @Test
    public void testGenerateAccountNumber_RandomNumber() {
        // Arrange & Act
        int result = accountService.generateAccountNumber();

        // Assert
        assertTrue(result >= 1000000000 && result <= Integer.MAX_VALUE);
    }

}
