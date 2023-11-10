package com.example.jeliBankBackend.context;
import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Account;
import com.example.jeliBankBackend.repository.AccountRepository;
import com.example.jeliBankBackend.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AcountServiceTests {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

//    @Test
//    void createAccount_Success() {
//        // Arrange
//        AccountRequestDto requestDto = new AccountRequestDto("John Doe", 1000.0);
//        int generatedAccountNumber = 123456;
//        //when(accountRepository.getAccountByAccountNumber(anyInt())).thenReturn(Optional.empty());
//        when(accountRepository.save(any(Account.class))).thenReturn(new Account(generatedAccountNumber, "John Doe", 1000.0));
//
//        // Act
//        AccountResponseDto responseDto = null;
//        try {
//            responseDto = accountService.createAccount(requestDto);
//        } catch (ResourseNotFoundException e) {
//            fail("Exception not expected: " + e.getMessage());
//        }
//
//        // Assert
//        assertNotNull(responseDto);
//        assertEquals("John Doe", responseDto.getOwnerName());
//        assertEquals(1000.0, responseDto.getInitialAmount());
//    }
}
