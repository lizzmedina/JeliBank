package com.example.jeliBankBackend.repository;

import com.example.jeliBankBackend.dtos.requests.AccountRequestDto;
import com.example.jeliBankBackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> getAccountByAccountNumber(int accountNumber);
}
