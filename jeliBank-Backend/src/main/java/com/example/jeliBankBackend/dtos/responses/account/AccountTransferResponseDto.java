package com.example.jeliBankBackend.dtos.responses.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTransferResponseDto {
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private double amount;

    public AccountTransferResponseDto(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }
}
