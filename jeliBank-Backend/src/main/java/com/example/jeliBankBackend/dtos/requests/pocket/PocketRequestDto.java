package com.example.jeliBankBackend.dtos.requests.pocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketRequestDto {
    private int accountNumber;
    private String name;
    private double balance;

    public PocketRequestDto(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

}
