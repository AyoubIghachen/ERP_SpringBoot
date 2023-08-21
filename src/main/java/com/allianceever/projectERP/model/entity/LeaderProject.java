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
@Table(name = "leaderProject")
public class LeaderProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaderProjectID;
    private String projectID;
    private String leaderID;
    private String first_Name;
    private String last_Name;
    private String designation;
    private String imageName;
}
