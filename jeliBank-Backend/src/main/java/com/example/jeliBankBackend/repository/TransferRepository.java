package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    static Optional<Transfer> findTransfersByClient(Optional<Client> client) {
        return Optional.of(new Transfer()); // agregar consulta sql para traer las transacciones asociadas al cliente
    }
}
