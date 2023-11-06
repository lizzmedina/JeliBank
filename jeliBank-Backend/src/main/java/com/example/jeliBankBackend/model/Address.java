package com.example.jeliBankBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private Long addressId;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "neighborhood")
    private String neighborhood;
    @Column(name = "houseType")
    private String houseType;
//    @Column
//    private Long client_id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;


}
