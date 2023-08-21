package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProjectDto {
    private Long imageProjectID;
    private String projectID;
    private String imageName;
    private String originalName;
}
