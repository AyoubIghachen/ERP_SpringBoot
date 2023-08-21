package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long taskID;
    private String projectID;
    private String task_Name;
    private String task_Priority;
    private String due_Date;
    private String description;
    private String status;
}
