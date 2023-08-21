package com.allianceever.projectERP.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="expenses")
public class Expenses {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String itemName;

    private String purchaseFrom;


    @Column(name="purchase_date")
    private String purchaseDate;


    private String purchasedBy;

    private String Amount;


    private String paidBy;

    private String Status;


    @Lob
    private byte[] data;
}
