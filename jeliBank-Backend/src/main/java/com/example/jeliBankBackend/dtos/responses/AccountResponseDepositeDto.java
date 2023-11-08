package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDepositeDto {
    private Double amount;

    public AccountResponseDepositeDto(Double amount) {
        this.amount = amount;
    }
}
