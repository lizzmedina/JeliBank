package com.example.jeliBankBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Transfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    private Date transferDate;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "acountId")
    @JsonBackReference
    private Long originAcount;

    @ManyToOne
    @JoinColumn(name = "acountId")
    @JsonBackReference
    private Long targetAcount;


    // -----------getters and setters----------------

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getOriginAcount() {
        return originAcount;
    }

    public void setOriginAcount(Long originAcount) {
        this.originAcount = originAcount;
    }

    public Long getTargetAcount() {
        return targetAcount;
    }

    public void setTargetAcount(Long targetAcount) {
        this.targetAcount = targetAcount;
    }
}

