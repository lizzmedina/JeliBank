package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Acount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcountRepository extends JpaRepository<Acount, Long> {
}
