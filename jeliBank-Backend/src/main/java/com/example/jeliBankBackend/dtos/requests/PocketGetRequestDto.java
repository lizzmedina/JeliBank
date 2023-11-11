package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketGetRequestDto {
    private int accountNumber;

    public PocketGetRequestDto(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
