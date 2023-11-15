package com.example.jeliBankBackend.dtos.requests.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    private String userName;
    private String password;

    public UserLoginDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
