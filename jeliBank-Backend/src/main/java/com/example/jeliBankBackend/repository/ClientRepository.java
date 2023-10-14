package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
