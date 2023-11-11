package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDepositeResponseDto {
    private Double amount;

    public AccountDepositeResponseDto(Double amount) {
        this.amount = amount;
    }
}
