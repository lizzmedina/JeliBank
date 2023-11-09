package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseGetDto {
    private int accountNumber;
    private String ownerName;
    private  double balance;

    public AccountResponseGetDto() {
    }

    public AccountResponseGetDto(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public AccountResponseGetDto(int accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }
}
