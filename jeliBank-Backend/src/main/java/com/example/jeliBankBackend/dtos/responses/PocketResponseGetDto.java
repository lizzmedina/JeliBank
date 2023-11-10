package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketResponseGetDto {
    private String name;
    private int pocketNumber;
    private double amount;

    public PocketResponseGetDto(String name, int pocketNumber, double amount) {
        this.name = name;
        this.pocketNumber = pocketNumber;
        this.amount = amount;
    }
}
