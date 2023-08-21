package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long employeeID;
    private String first_Name;
    private String last_Name;
    private String userName;
    private String email;
    private String password;
    private String joinDate;
    private String phone;
    private String departement;
    private String designation;
    private String company;
    private Integer remainingLeaves;
    private String role;
    private Double pinCode;
    private String cv_Name;
    private Byte cv;
    private String cin;
    private String reportTo;
    private String birthday;
    private String address;
    private String gender;
    private String state;
    private String country;
    private String imageName;
}
