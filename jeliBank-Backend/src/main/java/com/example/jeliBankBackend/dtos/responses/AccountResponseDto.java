package com.example.jeliBankBackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {
    private String ownerName;
    private Double balance;

    public AccountResponseDto() {
    }

    public AccountResponseDto( String ownerName, Double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

}
