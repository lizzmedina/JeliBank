package com.example.jeliBankBackend.dtos.responses;

public class AccountResponseTransferDto {
    private String message;

    public AccountResponseTransferDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

