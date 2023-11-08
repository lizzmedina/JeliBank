package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PocketRequestDto {
    private String poketName;
    private double balance;

    public PocketRequestDto(String poketName, double balance) {
        this.poketName = poketName;
        this.balance = balance;
    }

}
