package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBlockRequestDto {
    private int accountNumber;

    public AccountBlockRequestDto(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
