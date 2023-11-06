package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNumberDocumentId(Long numberDocumentId);
}
