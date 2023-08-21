package com.allianceever.projectERP.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskID;
    private String projectID;
    private String task_Name;
    private String task_Priority;
    private String due_Date;
    @Size(max = 1000)
    private String description;
    private String status;
}
