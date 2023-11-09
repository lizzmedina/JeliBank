package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PocketResponseDto {
    private int accountNumber;
    private String name;
    private double balance;

    public PocketResponseDto(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }
}
