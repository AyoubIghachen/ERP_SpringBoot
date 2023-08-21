package com.allianceever.projectERP.model.dto;

import com.allianceever.projectERP.model.entity.EstimatesInvoices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {


    private Integer itemID;





    private String name;

    private String description;

    private double uniteCost;

    private Integer quantity;

    private double amount;

    private EstimatesInvoicesDto EstimateInvoices;

}
