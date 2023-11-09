package com.example.jeliBankBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pocket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int pocketNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;

    public Pocket(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
}
