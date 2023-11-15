package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Role;
import com.example.jeliBankBackend.utils.RolesName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    // Method to search for a role throw the name/role in our data base
    Optional<Role> findByRole(RolesName role);
}
