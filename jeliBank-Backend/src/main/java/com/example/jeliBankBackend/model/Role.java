package com.example.jeliBankBackend.model;

import com.example.jeliBankBackend.utils.RolesName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "roles" )
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;
    @Enumerated(EnumType.STRING)
    private RolesName role ;

    public Role(RolesName authorityName) {
        this.role = authorityName;
    }

}