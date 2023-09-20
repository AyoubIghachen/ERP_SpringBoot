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
@Table(name="Leaves")
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer leavesID;

    private String  username;

    private String  EmployeeName;

    private String LeaveType;

    private Integer NumberOfDays;

    @Column(name="start_date")
    private String StartDate;

    private String EndDate;

    private String LeaveReason;

    private String ApprovedBy;

    private String Status;

}
