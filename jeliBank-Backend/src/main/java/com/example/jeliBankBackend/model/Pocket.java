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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pocketNumber;

    private String poketName;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "accounNumber")
    @JsonBackReference
    private Account account;

    public Pocket(String poketName, double balance) {
        this.poketName = poketName;
        this.balance = balance;
    }
}
