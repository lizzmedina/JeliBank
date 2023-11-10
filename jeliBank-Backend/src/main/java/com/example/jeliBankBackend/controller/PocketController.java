package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketTransferRequestDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseGetDto;
import com.example.jeliBankBackend.dtos.responses.PocketTransferResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.AccountService;
import com.example.jeliBankBackend.service.PocketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pockets")
public class PocketController {
    private final AccountService accountService;
    private final PocketService pocketService;

    public PocketController(AccountService accountService, PocketService pocketService) {
        this.accountService = accountService;
        this.pocketService = pocketService;
    }

    // 1- crear bolsillo
    @PostMapping()
    public ResponseEntity<PocketResponseDto> createPocket(@RequestBody PocketRequestDto requestDto) throws ResourseNotFoundException {
        PocketResponseDto pocketResponse = pocketService.createPocket(requestDto);
        return ResponseEntity.ok(pocketResponse);
    }

    // 2- transferir a bolsillos
    @PostMapping("transfer")
    public ResponseEntity<PocketTransferResponseDto> transferToPocket(@RequestBody PocketTransferRequestDto pocket) throws ResourseNotFoundException {
        PocketTransferResponseDto response = this.pocketService.transferToPocket(pocket);
        return ResponseEntity.ok(response);
    }
    // 3 - consultar bolsillos
    @GetMapping("{accountNumber}/pockets")
    public List<PocketResponseGetDto> getPocketList(@PathVariable("accountNumber") int accountNumber) throws ResourseNotFoundException {
        System.out.println("account number from  controller" + accountNumber);
        List<PocketResponseGetDto> response = this.pocketService.getPocketsByAccount(accountNumber);
        return response;
    }
}
