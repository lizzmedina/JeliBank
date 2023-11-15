package com.example.jeliBankBackend.security;


import com.example.jeliBankBackend.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@AllArgsConstructor
public class SecurityRole implements GrantedAuthority {
    private final Role role;

    @Override
    public String getAuthority() {
        return role.getRole().toString();
    }

}
