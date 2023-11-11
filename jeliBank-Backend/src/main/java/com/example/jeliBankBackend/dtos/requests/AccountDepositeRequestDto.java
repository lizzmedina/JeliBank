package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountDepositeRequestDto {
    private Double amountToDeposite;

    public AccountDepositeRequestDto() {
    }

    public AccountDepositeRequestDto(Double amountToDeposite) {

        this.amountToDeposite = amountToDeposite;
    }
}
