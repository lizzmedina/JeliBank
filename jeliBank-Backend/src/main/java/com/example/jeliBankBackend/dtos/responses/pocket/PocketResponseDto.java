package com.example.jeliBankBackend.dtos.responses.pocket;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PocketResponseDto {
    private int accountNumber;
    private String name;
    private double initialBalance;

    public PocketResponseDto(int accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.initialBalance = initialBalance;
    }
}
