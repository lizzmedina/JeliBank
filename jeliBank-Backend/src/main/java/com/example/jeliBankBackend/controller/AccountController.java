package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountStatusRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferToAccountRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDepositeDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseGetDto;
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
    @PostMapping("/{accountNumber}/transfer")
    public AccountResponseDepositeDto depositIntoAccount(@PathVariable("accountNumber") int accountNumber, @RequestBody AccountTransferRequestDto depositRequest) throws ResourseNotFoundException {
        AccountResponseDepositeDto response = this.accountService.depositIntoAccount(accountNumber, depositRequest.getAmountToDeposite());
        return response;
    }

    // 3- transferencia entre cuentas
    @PostMapping("/transfer")
    public AccountTransferToAccountRequestDto transferBetweenAccounts( @RequestBody AccountTransferToAccountRequestDto transferRequest) throws ResourseNotFoundException {
            AccountTransferToAccountRequestDto response = accountService.transferBetweenAccounts(transferRequest);
            return ResponseEntity.ok(response).getBody();
    }

    // 4- consultar cuenta
    @GetMapping("/{accountNumber}")
    public Optional<AccountResponseGetDto> getAccountDetails(@PathVariable("accountNumber") int accountNumber) throws ResourseNotFoundException {
        Optional<AccountResponseGetDto> response = this.accountService.getAccountDetails(accountNumber);
        return response;
    }

    //5- bloquear cuenta
    @PutMapping("/block")
    public ResponseEntity<String> blockAccount(@RequestBody AccountStatusRequestDto requestDto) throws ResourseNotFoundException {
            String response = accountService.toggleAccountStatus(requestDto);
            return ResponseEntity.ok(response);
    }

//    @DeleteMapping("/acount/{acountNumber}")
//    public ResponseEntity<?> deleteAcount(@PathVariable Long acountNumber) throws ResourseNotFoundException{
//        accountService.deleteAcount(acountNumber);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
