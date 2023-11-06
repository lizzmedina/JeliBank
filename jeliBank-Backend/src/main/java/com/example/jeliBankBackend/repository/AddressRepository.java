package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // hacer consulta a la bd para traer la direccion asociada al documento del cliente
    Optional<Address> findByClient_NumberDocumentId(Long documentClient);
}
