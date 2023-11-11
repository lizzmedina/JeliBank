package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.*;
import com.example.jeliBankBackend.dtos.responses.AccountBlockResponseDto;
import com.example.jeliBankBackend.dtos.responses.AccountDepositeResponseDto;
import com.example.jeliBankBackend.dtos.responses.AccountGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.AccountTransferResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    // 1- apertura de  cuenta
    @PostMapping()
    public AccountRequestDto createAcount (@RequestBody AccountRequestDto accountRequestDto) throws ResourseNotFoundException {
        this.accountService.createAccount(accountRequestDto);
        return accountRequestDto;
    }

    // 2- deposito en cuenta
    @PostMapping("/{accountNumber}/deposit")
    public AccountDepositeResponseDto depositIntoAccount(@PathVariable("accountNumber") int accountNumber, @RequestBody AccountDepositeRequestDto depositRequest) throws ResourseNotFoundException {
        AccountDepositeResponseDto response =  this.accountService.depositIntoAccount(accountNumber, depositRequest);
        return response;
    }

    // 3- transferencia entre cuentas
    @PostMapping("/transfer")
    public AccountTransferResponseDto transferBetweenAccounts(@RequestBody AccountTransferRequestDto transferRequest) throws ResourseNotFoundException {
        AccountTransferResponseDto response = accountService.transferBetweenAccounts(transferRequest);
            return ResponseEntity.ok(response).getBody();
    }

    // 4- consultar cuenta
    @GetMapping("/{accountNumber}")
    public Optional<AccountGetResponseDto> getAccountDetails(@PathVariable("accountNumber") AccountGetRequestDto accountNumber) throws ResourseNotFoundException {
        int account = accountNumber.getAccountNumber();
        Optional<AccountGetResponseDto> response = this.accountService.getAccountDetails(account);
        return response;
    }

    //5- bloquear cuenta
    @PutMapping("/block")
    public AccountBlockResponseDto blockAccount(@RequestBody AccountBlockRequestDto requestDto) throws ResourseNotFoundException {
          return accountService.toggleAccountStatus(requestDto);
    }

//   @DeleteMapping("/acount/{acountNumber}")
//    public ResponseEntity<?> deleteAcount(@PathVariable Long acountNumber) throws ResourseNotFoundException{
//        accountService.deleteAcount(acountNumber);
//        return new ResponseEntity<>(HttpStatus.OK);
//   }
}
