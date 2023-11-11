package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBlockResponseDto {
    private String message;

    public AccountBlockResponseDto(String message) {
        this.message = message;
    }
}

