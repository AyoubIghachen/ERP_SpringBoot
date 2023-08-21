package com.allianceever.projectERP.model.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesDto {

    private Integer Id;

    private String itemName;

    private String purchaseFrom;


    private String purchaseDate;


    private String purchasedBy;

    private String Amount;


    private String paidBy;

    private String Status;


    @Lob
    private byte[] data;
}
