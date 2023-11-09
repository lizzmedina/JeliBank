package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTransferToAccountRequestDto {
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private double amount;

    public AccountTransferToAccountRequestDto() {
    }

    public AccountTransferToAccountRequestDto(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }
}
