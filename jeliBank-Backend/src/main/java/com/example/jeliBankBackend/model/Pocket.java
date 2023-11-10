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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pocket_sequence_generator")
    @SequenceGenerator(name = "pocket_sequence_generator", sequenceName = "pocket_sequence", allocationSize = 1)
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
