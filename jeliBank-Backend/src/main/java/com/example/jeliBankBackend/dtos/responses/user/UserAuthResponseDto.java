package com.example.jeliBankBackend.dtos.responses.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthResponseDto {
    private String accessToken;

    public UserAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
