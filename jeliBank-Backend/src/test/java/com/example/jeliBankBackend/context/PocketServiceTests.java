package com.example.jeliBankBackend.context;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketTransferRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseGetDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketTransferResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.model.Pocket;
import com.example.jeliBankBackend.repository.AccountRepository;
import com.example.jeliBankBackend.repository.PocketRepository;
import com.example.jeliBankBackend.service.AccountService;
import com.example.jeliBankBackend.service.PocketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PocketServiceTests {
    @Mock
    private PocketRepository pocketRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private PocketService pocketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreatePocket_Success() throws ResourseNotFoundException {
//        // Arrange
//        AccountRequestDto accountRequestDto = new AccountRequestDto("John Doe", 1000.0);
//        AccountResponseGetDto accountResponseGetDto = new AccountResponseGetDto(1, "John Doe", 500.0);
//        PocketRequestDto pocket = new PocketRequestDto(1, "MyPocket", 100.0);
//
//        // Mock external dependencies
//        when(accountService.getAccountDetails(1)).thenReturn(Optional.of(accountResponseGetDto));
//        when(accountService.AccountResponseGetDtotoEntity(any())).thenReturn(new Account()); // Simula una cuenta válida
//        when(pocketRepository.save(any())).thenReturn(new Pocket()); // Simula la creación exitosa de un bolsillo
//
//        // Act
//        PocketResponseDto result = pocketService.createPocket(pocket);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getAccountNumber());
//        assertEquals("MyPocket", result.getName());
//        assertEquals(100.0, result.getInitialBalance());
//        Mockito.verify(accountService, Mockito.times(1)).updateAccountBalance(1, 400.0);
//        Mockito.verify(pocketRepository, Mockito.times(1)).save(any());
//    }
//
//    @Test
//    void testCreatePocket_InsufficientBalance() throws ResourseNotFoundException {
//        // Arrange
//        PocketRequestDto pocketRequestDto = new PocketRequestDto(1, "MyPocket", 1000.0);
//    // Mock external dependencies
//        when(accountService.getAccountDetails(1)).thenReturn(Optional.of(new AccountResponseGetDto(1, "John Doe", 500.0)));
//
//        // Act and Assert
//        assertThrows(ResourseNotFoundException.class, () -> pocketService.createPocket(pocketRequestDto));
//    }

// este funciona -> ok
    @Test
    public void testCreatePocket_AccountNotFound() throws ResourseNotFoundException {
        // Arrange
        PocketRequestDto pocketRequestDto = new PocketRequestDto(1, "MyPocket", 100.0);

        when(accountService.getAccountDetails(1)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> pocketService.createPocket(pocketRequestDto));
    }

//
//
//    // TRANSFER
//    @Test
//    public void testTransferToPocket_SuccessfulTransfer() throws ResourseNotFoundException {
//        // Arrange
//        PocketTransferRequestDto transferRequestDto = new PocketTransferRequestDto();
//        Account account = new Account(1, "John Doe", 100);
//        Pocket pocket = new Pocket("MyPocket", 50);
//
//        when(accountRepository.getAccountByAccountNumber(1)).thenReturn(Optional.of(account));
//        when(pocketRepository.findById(100)).thenReturn(Optional.of(pocket));
//
//        // Act
//        PocketTransferResponseDto result = pocketService.transferToPocket(transferRequestDto);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getAccountNumber());
//        assertEquals(100, result.getPocketNumber());
//        assertEquals(20, result.getAmount());
//
//        // Verify interactions
//        verify(accountService, times(1)).updateAccountBalance(1, 80);
//        verify(pocketRepository, times(1)).save(pocket);
//    }
//
//    @Test
//    public void testTransferToPocket_InsufficientAccountBalance() throws ResourseNotFoundException {
//        // Arrange
//        PocketTransferRequestDto transferRequestDto = new PocketTransferRequestDto();
//        Account account = new Account(1, "John Doe", 100);
//        Pocket pocket = new Pocket( "MyPocket", 50);
//
//        when(accountRepository.getAccountByAccountNumber(1)).thenReturn(Optional.of(account));
//        when(pocketRepository.findById(100)).thenReturn(Optional.of(pocket));
//
//        // Act and Assert
//        assertThrows(ResourseNotFoundException.class, () -> pocketService.transferToPocket(transferRequestDto));
//
//        // Verify interactions
//        verify(accountService, never()).updateAccountBalance(anyInt(), anyDouble());
//        verify(pocketRepository, never()).save(pocket);
//    }
//
//    @Test
//    public void testTransferToPocket_AccountNotFound() throws ResourseNotFoundException {
//        // Arrange
//        PocketTransferRequestDto transferRequestDto = new PocketTransferRequestDto();
//
//        when(accountRepository.getAccountByAccountNumber(1)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(ResourseNotFoundException.class, () -> pocketService.transferToPocket(transferRequestDto));
//
//        // Verify interactions
//        verify(accountService, never()).updateAccountBalance(anyInt(), anyDouble());
//        verify(pocketRepository, never()).save(any());
//    }
//
//    @Test
//    public void testTransferToPocket_PocketNotFound() throws ResourseNotFoundException {
//        // Arrange
//        PocketTransferRequestDto transferRequestDto = new PocketTransferRequestDto();
//        Account account = new Account(1, "John Doe", 100);
//
//        when(accountRepository.getAccountByAccountNumber(1)).thenReturn(Optional.of(account));
//        when(pocketRepository.findById(100)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(ResourseNotFoundException.class, () -> pocketService.transferToPocket(transferRequestDto));
//
//        // Verify interactions
//        verify(accountService, never()).updateAccountBalance(anyInt(), anyDouble());
//        verify(pocketRepository, never()).save(any());
//    }
}
