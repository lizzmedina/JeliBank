package com.example.jeliBankBackend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountRequestDto {
   private String ownerName;
    private  double balance;
    public AccountRequestDto(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public AccountRequestDto() {
    }
}
