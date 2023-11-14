package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    //Method to search an user in the data base by his userName
    Optional<Users> findByUsername(String username);

    // Method to verify if an user exist in our data base
    Boolean existsByUsername(String username);
}
