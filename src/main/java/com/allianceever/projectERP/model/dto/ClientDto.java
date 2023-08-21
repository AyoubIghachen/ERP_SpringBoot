package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long clientID;
    private String first_Name;
    private String last_Name;
    private String gender;
    private String designation;
    private String personnel_Email;
    private String personnel_Phone;
    private String imageName;

    private String company_Name;
    private String date_Creation;
    private String address;
    private String ice;
    private String rc;
    private String ville;
    private String capital;
    private String rib;
    private String company_Email;
    private String company_Phone;
    private String website;
}
