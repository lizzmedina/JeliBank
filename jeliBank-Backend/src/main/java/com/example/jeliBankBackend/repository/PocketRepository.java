package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketRepository extends JpaRepository<Pocket, Long> {
}
