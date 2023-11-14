package com.example.jeliBankBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles" )
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;
    private String name ;

    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }

}