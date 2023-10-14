package com.example.jeliBankBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Acount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acountId;

    private  double balance;

    private Long acountNumber;

    private String acountType;

    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;

    // -----------getters and setters----------------

    public Long getId() {
        return acountId;
    }

    public void setId(Long acountId) {
        this.acountId = acountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getAcountNumber() {
        return acountNumber;
    }

    public void setAcountNumber(Long acountNumber) {
        this.acountNumber = acountNumber;
    }

    public String getAcountType() {
        return acountType;
    }

    public void setAcountType(String acountType) {
        this.acountType = acountType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
