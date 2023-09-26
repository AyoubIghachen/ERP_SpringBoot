package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long projectID;
    private String project_Name;
    private String company_Name;
    private String start_Date;
    private String end_Date;
    private String rate;
    private String rate_Type;
    private String priority;
    private String total_Hours;
    private String status;
    private String created_By;
    private String description;

    private String progress;
    private List<LeaderProjectDto> leaderProjectDtoList;
    private List<EmployeeProjectDto> employeeProjectDtoList;
}
