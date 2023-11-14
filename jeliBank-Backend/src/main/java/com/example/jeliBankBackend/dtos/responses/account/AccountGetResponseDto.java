package com.example.jeliBankBackend.dtos.responses.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountGetResponseDto {
    private int accountNumber;
    private String ownerName;
    private  double balance;

    public AccountGetResponseDto() {
    }

    public AccountGetResponseDto(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public AccountGetResponseDto(int accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }
}
