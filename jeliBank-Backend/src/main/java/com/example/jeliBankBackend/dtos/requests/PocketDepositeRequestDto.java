package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketDepositeRequestDto {
    private int accountNumber;
    private int pocketNumber;
    private double amount;
}
