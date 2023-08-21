package com.allianceever.projectERP.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    private Long invoiceID;
    private String paidDate;
    private BigDecimal paidAmount;

}
