package com.example.jeliBankBackend.context.service;

import com.example.jeliBankBackend.dtos.requests.pocket.PocketRequestDto;
import com.example.jeliBankBackend.dtos.responses.account.AccountGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketResponseDto;
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
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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



    @Test
    public void testCreatePocket_SuccessfulCreation() throws ResourseNotFoundException {
        // Arrange
        int accountNumber = 123456;
        PocketRequestDto pocketRequestDto = new PocketRequestDto(accountNumber, "MyPocket", 50.0);
        Account account = new Account(accountNumber, "John Doe", 100.0);

        when(accountService.getAccountDetails(any())).thenReturn(Optional.of(new AccountGetResponseDto(accountNumber, "John Doe", 100.0)));
        when(accountService.AccountResponseGetDtotoEntity(any())).thenReturn(account);
        when(pocketRepository.save(any())).thenReturn(new Pocket());

        // Act
        PocketResponseDto result = pocketService.createPocket(pocketRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals("MyPocket", result.getName());
        assertEquals(50.0, result.getInitialBalance());
    }

    @Test
    public void testCreatePocket_AccountNotFound() throws ResourseNotFoundException {
        // Arrange
        int accountNumber = 123456;
        PocketRequestDto pocketRequestDto = new PocketRequestDto(accountNumber, "MyPocket", 50.0);

        when(accountService.getAccountDetails(any())).thenThrow(new ResourseNotFoundException("Cuenta no encontrada"));

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> pocketService.createPocket(pocketRequestDto));
    }

//    @Test
//    public void testCreatePocket_InsufficientBalance() throws ResourseNotFoundException {
//        // Arrange
//        int accountNumber = 123456;
//        PocketRequestDto pocketRequestDto = new PocketRequestDto(accountNumber, "MyPocket", 150.0);
//
//        when(accountService.getAccountDetails(any()))
//                .thenReturn(Optional.of(new AccountGetResponseDto(accountNumber, "John Doe", 100.0)));
//
//        // Act & Assert
//        assertThrows(ResourseNotFoundException.class, () -> pocketService.createPocket(pocketRequestDto));
//    }



}
