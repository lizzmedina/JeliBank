package com.example.jeliBankBackend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDto {
    private String role;

    public RoleRequestDto() {
    }

    @JsonCreator
    public RoleRequestDto(@JsonProperty("role") String role) {
        this.role = role;
    }
}
