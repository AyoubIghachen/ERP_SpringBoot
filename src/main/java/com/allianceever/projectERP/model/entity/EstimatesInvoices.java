package com.allianceever.projectERP.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="estimates_invoices")

public class EstimatesInvoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    private Integer clientID;

    private Integer projectID;

    private String createDate;

    private  String estimateDate	;

    private String expiryDate;

    private BigDecimal total;

    private String otherInfo;

    private String status;

    private Integer tax;


    @OneToMany(mappedBy = "estimateInvoices",  cascade = CascadeType.ALL)
    private List<Item> items ;


}
