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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping()
    public AccountRequestDto createAcount (@RequestBody AccountRequestDto accountRequestDto) throws ResourseNotFoundException {
        this.accountService.createAccount(accountRequestDto);
        return accountRequestDto;
    }

    @PostMapping("/{accountNumber}/transfer")
    public ResponseEntity<AccountResponseDepositeDto> depositIntoAccount(
            @PathVariable("accountNumber") int accountNumber,
            @RequestBody AccountTransferRequestDto depositRequest) {
        try {
            double newBalance = accountService.depositIntoAccount(accountNumber, depositRequest.getAmountToDeposite());
            AccountResponseDepositeDto response = new AccountResponseDepositeDto(newBalance);
            return ResponseEntity.ok(response);
        } catch (ResourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AccountResponseDepositeDto(0.0));
        }
    }
    @PostMapping("/transfer")
    public AccountTransferToAccountRequestDto transferBetweenAccounts(
            @RequestBody AccountTransferToAccountRequestDto transferRequest) {
        try {
            AccountTransferToAccountRequestDto response = accountService.transferBetweenAccounts(transferRequest);
            return ResponseEntity.ok(response).getBody();
        } catch (ResourseNotFoundException e) {
            return transferRequest;
        }
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseGetDto> getAccountDetails(@PathVariable("accountNumber") int accountNumber) {
        try {
            Optional<AccountRequestDto> optionalAccount = accountService.getAcountByNumber(accountNumber);

            if (optionalAccount.isPresent()) {
                AccountRequestDto accountDto = optionalAccount.get();

                AccountResponseGetDto response = new AccountResponseGetDto(
                        accountNumber,
                        accountDto.getOwnerName(),
                        accountDto.getBalance()
                );

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AccountResponseGetDto(0, "", 0.0));
        }
    }

    @PutMapping("/block")
    public ResponseEntity<String> toggleAccountStatus(@RequestBody AccountStatusRequestDto requestDto) {
        try {
            String response = accountService.toggleAccountStatus(requestDto);
            return ResponseEntity.ok(response);
        } catch (ResourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
        }
    }

//    @DeleteMapping("/acount/{acountNumber}")
//    public ResponseEntity<?> deleteAcount(@PathVariable Long acountNumber) throws ResourseNotFoundException{
//        accountService.deleteAcount(acountNumber);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
