package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {
    private String ownerName;
    private Double initialAmount;

    public AccountResponseDto() {
    }

    public AccountResponseDto(String ownerName, Double initialAmount) {
        this.ownerName = ownerName;
        this.initialAmount = initialAmount;
    }

}
