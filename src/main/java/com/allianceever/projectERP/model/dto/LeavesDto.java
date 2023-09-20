package com.allianceever.projectERP.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeavesDto {

    private Integer LeavesID;

    private String  username;

    private String  EmployeeName;

    private String LeaveType;

    private Integer NumberOfDays;

    private String StartDate;

    private String EndDate;

    private String LeaveReason;

    private String ApprovedBy;

    private String Status;
}
