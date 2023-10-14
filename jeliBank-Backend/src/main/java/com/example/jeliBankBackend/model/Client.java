package com.example.jeliBankBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_id;

    private Long numberDocumentId;
    private String name;

    private String lastName;

    // -----------getters and setters----------------

    public Long getNumberDocumentId() {
        return numberDocumentId;
    }

    public void setNumberDocumentId(Long numberDocumentId) {
        this.numberDocumentId = numberDocumentId;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
