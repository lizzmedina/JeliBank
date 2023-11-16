package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.pocket.PocketGetRequestDto;
import com.example.jeliBankBackend.dtos.requests.pocket.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.pocket.PocketDepositeRequestDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketGetResponseDto;
import com.example.jeliBankBackend.dtos.responses.pocket.PocketDepositeResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.AccountService;
import com.example.jeliBankBackend.service.PocketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/pockets/")
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
    @PostMapping("deposit")
    public ResponseEntity<PocketDepositeResponseDto> transferToPocket(@RequestBody PocketDepositeRequestDto pocket) throws ResourseNotFoundException {
        PocketDepositeResponseDto response = this.pocketService.transferToPocket(pocket);
        return ResponseEntity.ok(response);
    }
    // 3 - consultar bolsillos
    @GetMapping("{accountNumber}")
    public List<PocketGetResponseDto> getPocketList(@PathVariable("accountNumber") int accountNumber) throws ResourseNotFoundException {
        PocketGetRequestDto pocketGetRequestDto = new PocketGetRequestDto(accountNumber);
        pocketGetRequestDto.setAccountNumber(accountNumber);
        //int account = accountNumber.getAccountNumber();
        List<PocketGetResponseDto> response = this.pocketService.getPocketsByAccount(pocketGetRequestDto);
        return response;
    }

}
