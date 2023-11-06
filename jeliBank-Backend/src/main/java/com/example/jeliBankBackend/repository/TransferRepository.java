package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    // agregar consulta sql para traer las transacciones asociadas al cliente
     List<Transfer> findByOriginAcount_Client(Client client);

}
