package com.example.jeliBankBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @Column(name = "account_number")
    private int accountNumber;

    @Column(name = "ownerName")
    private String ownerName;

    @Column(name = "balance")
    private  double balance;

    @Column(name = "isActive")
    private boolean isActive = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Pocket> pockets = new ArrayList<>();

    public Account(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public Account(int accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }


    // ... otros métodos ...

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}


