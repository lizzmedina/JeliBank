package com.example.jeliBankBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long client_id;

    @Column(name = "numberDocumentId")
    private Long numberDocumentId;

    @Column(name = "lastName")
    private String lastName;
    @Column(name = "name")
    private String name;






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
