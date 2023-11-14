package com.example.jeliBankBackend.dtos.requests.account;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountRequestDto {
    private String ownerName;
    private Double initialAmount;

    public AccountRequestDto( String ownerName, Double initialAmount) {

        this.ownerName = ownerName;
        this.initialAmount = initialAmount;
    }
}
