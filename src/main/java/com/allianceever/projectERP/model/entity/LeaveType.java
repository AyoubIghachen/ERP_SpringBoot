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
@Table(name="LeaveType")
public class LeaveType {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer leaveTypeId;

    private String  username;

    @Column(unique = true)
    private String leaveName;

    private String days;

    private String leaveStatus;
}
