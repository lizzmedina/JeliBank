package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketTransferRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseGetDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
import com.example.jeliBankBackend.dtos.responses.PocketTransferResponseDto;
import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.service.AccountService;
import com.example.jeliBankBackend.service.PocketService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    // 1- transferir a bolsillos
    @PostMapping("transfer")
    public ResponseEntity<PocketTransferResponseDto> transferToPocket(@RequestBody PocketTransferRequestDto pocket) throws ResourseNotFoundException {
        PocketTransferResponseDto response = this.pocketService.transferToPocket(pocket);
        return ResponseEntity.ok(response);
    }


//    @PostMapping("/transfer")
//    public PocketRequestDto
}
