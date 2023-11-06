package com.example.jeliBankBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "addresses")
    private final List<Address> addresses = new ArrayList<>();

    public Client() {
    }

    public Client(Long numberDocumentId, String lastName, String name) {
        this.numberDocumentId = numberDocumentId;
        this.lastName = lastName;
        this.name = name;
    }

    public Client(Long client_id, Long numberDocumentId, String lastName, String name) {
        this.client_id = client_id;
        this.numberDocumentId = numberDocumentId;
        this.lastName = lastName;
        this.name = name;
    }
}
