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
@Table(name = "imageProject")
public class ImageProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageProjectID;
    private String projectID;
    private String imageName;
    private String originalName;
}
