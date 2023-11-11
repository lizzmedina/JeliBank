package com.example.jeliBankBackend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTransferRequestDto {
    private int sourceAccountNumber;
    private int destinationAccountNumber;
    private double amountToTransfer;

    public AccountTransferRequestDto() {
    }

    public AccountTransferRequestDto(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amountToTransfer = amountToTransfer;
    }
}
