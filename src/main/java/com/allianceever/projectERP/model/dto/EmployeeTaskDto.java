package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTaskDto {
    private Long employeeTaskID;
    private String taskID;
    private String employeeID;
    private String first_Name;
    private String last_Name;
    private String designation;
    private String imageName;
}
