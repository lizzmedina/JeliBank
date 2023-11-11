package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.PocketGetRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketDepositeRequestDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketDepositeResponseDto;
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
    @PostMapping("/deposit")
    public ResponseEntity<PocketDepositeResponseDto> transferToPocket(@RequestBody PocketDepositeRequestDto pocket) throws ResourseNotFoundException {
        PocketDepositeResponseDto response = this.pocketService.transferToPocket(pocket);
        return ResponseEntity.ok(response);
    }
    // 3 - consultar bolsillos
    @GetMapping("{accountNumber}")
    public List<PocketGetResponseDto> getPocketList(@PathVariable("accountNumber") PocketGetRequestDto accountNumber) throws ResourseNotFoundException {
        int account = accountNumber.getAccountNumber();
        List<PocketGetResponseDto> response = this.pocketService.getPocketsByAccount(account);
        return response;
    }
}
