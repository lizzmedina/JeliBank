package com.example.jeliBankBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Pocket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pocketNumber;

    private String poketName;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;


    // -----------getters and setters----------------

    public Long getPocketNumber() {
        return pocketNumber;
    }

    public void setPocketNumber(Long pocketNumber) {
        this.pocketNumber = pocketNumber;
    }

    public String getPoketName() {
        return poketName;
    }

    public void setPoketName(String poketName) {
        this.poketName = poketName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
