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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;
    private String first_Name;
    private String last_Name;
    @Column(unique = true)
    private String userName;
    private String email;
    private String password;
    @Column(name = "joinDate")
    private String joinDate;
    private String phone;
    private String departement;
    private String designation;
    private String company;
    private Integer remainingLeaves;
    private String role;
    @Column(name = "pinCode")
    private Double pinCode;
    private String cv_Name;
    private Byte cv;
    @Column(unique = true)
    private String cin;
    private String reportTo;
    private String birthday;
    private String address;
    private String gender;
    private String state;
    private String country;
    private String imageName;
}
