package com.example.jeliBankBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles" )
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;
    @Column(name = "role_name",nullable = false)
    private String role ;

    public Role() {
    }

    public Role(Integer idRole, String role) {
        this.idRole = idRole;
        this.role = role;
    }
}