package com.example.jeliBankBackend.dtos.responses.pocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketDepositeResponseDto {
    private int accountNumber;
    private int pocketNumber;
    private double amount;

    public PocketDepositeResponseDto(int accountNumber, int pocketNumber, double amount) {
        this.accountNumber = accountNumber;
        this.pocketNumber = pocketNumber;
        this.amount = amount;
    }
}
