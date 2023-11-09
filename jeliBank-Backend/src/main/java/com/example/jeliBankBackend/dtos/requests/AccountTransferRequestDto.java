package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountTransferRequestDto {
    private int accountNumber;
    private Double amountToDeposite;

    public AccountTransferRequestDto() {
    }

    public AccountTransferRequestDto(int accountNumber, Double amountToDeposite) {
        this.accountNumber = accountNumber;
        this.amountToDeposite = amountToDeposite;
    }
}
