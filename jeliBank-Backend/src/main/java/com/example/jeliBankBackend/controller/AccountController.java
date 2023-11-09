package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferRequestDto;
import com.example.jeliBankBackend.dtos.requests.AccountTransferToAccountRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseDepositeDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//    @GetMapping()
//    public List<Acount> getAcounts(){
//        return this.acountService.getAcounts();
//    }
//    @GetMapping("/acounts/{acountId}")
//    public Optional<Acount> getAcountById(@PathVariable("acountId") Long acountId) throws ResourseNotFoundException {
//        return this.acountService.getAcountById(acountId);
//    }

//    @GetMapping("/acounts/{acountNumber}")
//    public Optional<Acount> getAcountByNumber(@PathVariable("acountNumber") Long acountNumber) throws ResourseNotFoundException {
//        return Optional.ofNullable(this.acountService.getAcountByNumber(acountNumber));
//    }
//    @PutMapping("/acount/{acountToDelete}")
//    public ResponseEntity<?> upDateAcount(@RequestBody Acount acountToDelete) throws ResourseNotFoundException{
//        acountService.upDateAcount(acountToDelete);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/acount/{acountNumber}")
//    public ResponseEntity<?> deleteAcount(@PathVariable Long acountNumber) throws ResourseNotFoundException{
//        acountService.deleteAcount(acountNumber);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
