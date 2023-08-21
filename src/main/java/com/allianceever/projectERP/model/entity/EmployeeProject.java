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
@Table(name = "employeeProject")
public class EmployeeProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeProjectID;
    private String projectID;
    private String employeeID;
    private String first_Name;
    private String last_Name;
    private String designation;
    private String imageName;
}
