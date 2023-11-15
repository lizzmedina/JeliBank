package com.example.jeliBankBackend.dtos.requests.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private Integer roleId;

    public UserDto(Integer userId, String username, String firstName, String lastName, Integer roleId) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
    }
}
