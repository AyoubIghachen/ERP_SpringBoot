package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderProjectDto {
    private Long leaderProjectID;
    private String projectID;
    private String leaderID;
    private String first_Name;
    private String last_Name;
    private String designation;
    private String imageName;
}
