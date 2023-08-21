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
@Table(name = "fileProject")
public class FileProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileProjectID;
    private String projectID;
    private String fileName;
    private String originalName;
    private String dateCreation;
}
