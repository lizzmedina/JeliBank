package com.example.jeliBankBackend.dtos.requests.user;

import lombok.Data;

@Data
public class UserLoginDto {

    private String userName;
    private String password;
}
