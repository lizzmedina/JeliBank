package com.example.jeliBankBackend.controller;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.dtos.requests.PocketRequestDto;
import com.example.jeliBankBackend.dtos.responses.AccountResponseGetDto;
import com.example.jeliBankBackend.dtos.responses.PocketResponseDto;
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

    @PostMapping()
    public ResponseEntity<PocketResponseDto> createPocket(@RequestBody PocketRequestDto requestDto) throws ResourseNotFoundException {
        try {
            // Obtener la cuenta asociada al número de cuenta proporcionado
            Optional<AccountResponseGetDto> accountOptional = accountService.getAccountDetails(requestDto.getAccountNumber());

            if (accountOptional.isPresent()) {
                AccountResponseGetDto accountDto = accountOptional.get();

                // Crear el bolsillo y asociarlo a la cuenta
                PocketResponseDto pocketResponse = new PocketResponseDto(requestDto.getAccountNumber(), requestDto.getName(), requestDto.getBalance());

                // Devolver la respuesta del bolsillo creado
                return ResponseEntity.ok(pocketResponse);
            } else {
                throw new ResourseNotFoundException("Cuenta no encontrada");
            }
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al crear el bolsillo: " + e.getMessage());
        }
    }

}
