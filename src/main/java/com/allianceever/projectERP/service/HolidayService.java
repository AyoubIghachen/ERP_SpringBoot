package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.entity.Holiday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HolidayService {



    HolidayDto getByHolidayName(String holidayName);



    HolidayDto create(HolidayDto holidayDto);

    HolidayDto update(String HolidayName,HolidayDto holidayDto);


    List<HolidayDto> getAllHolidaysOrderedByDate();

    void delete(String HolidayName);

}
