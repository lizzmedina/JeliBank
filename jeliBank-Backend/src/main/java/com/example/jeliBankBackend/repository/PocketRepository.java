package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.model.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PocketRepository extends JpaRepository<Pocket, Integer> {
}
