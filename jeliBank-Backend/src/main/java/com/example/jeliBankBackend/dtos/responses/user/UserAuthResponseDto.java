package com.example.jeliBankBackend.dtos.responses.user;

import lombok.Data;

@Data
public class UserAuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    public UserAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
