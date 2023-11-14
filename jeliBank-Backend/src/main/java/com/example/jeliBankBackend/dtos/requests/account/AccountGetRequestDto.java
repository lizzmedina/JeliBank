package com.example.jeliBankBackend.dtos.requests.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountGetRequestDto {
    private int accountNumber;

    public AccountGetRequestDto(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
