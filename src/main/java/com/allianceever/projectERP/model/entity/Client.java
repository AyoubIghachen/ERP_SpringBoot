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
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientID;
    private String first_Name;
    private String last_Name;
    private String gender;
    private String designation;
    private String personnel_Email;
    private String personnel_Phone;
    private String imageName;

    @Column(unique = true)
    private String company_Name;
    private String date_Creation;
    private String address;
    @Column(unique = true)
    private String ice;
    @Column(unique = true)
    private String rc;
    private String ville;
    private String capital;
    @Column(unique = true)
    private String rib;
    private String company_Email;
    private String company_Phone;
    private String website;
}
