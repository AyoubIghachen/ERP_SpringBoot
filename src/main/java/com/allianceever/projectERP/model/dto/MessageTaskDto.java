package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTaskDto {
    private Long messageTaskID;
    private String taskID;
    private String employeeID;
    private String first_Name;
    private String last_Name;
    private String imageName;
    private String date;
    private String message;
}
