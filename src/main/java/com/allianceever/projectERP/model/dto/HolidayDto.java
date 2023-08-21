package com.allianceever.projectERP.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDto {

    private Integer HolidayId;
    private String holidayName;
    private String holidayDate;
    private String holidayDateEnd;

}
