package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstimatesInvoicesDto {

    private Integer id;

    private String type;

    private Integer clientID;

    private Integer projectID;

    private String createDate;

    private  String estimateDate;

    private String expiryDate;

    private BigDecimal total;

    private String otherInfo;

    private String status;

    private Integer tax;


    private List<ItemDto> items;




}
