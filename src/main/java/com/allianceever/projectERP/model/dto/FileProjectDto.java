package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileProjectDto {
    private Long fileProjectID;
    private String projectID;
    private String fileName;
    private String originalName;
    private String dateCreation;
}
