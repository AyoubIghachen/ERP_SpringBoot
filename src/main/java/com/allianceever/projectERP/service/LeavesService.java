package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.dto.LeavesDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface LeavesService {

    LeavesDto getByLeavesID(Integer leavesID);



    LeavesDto create(LeavesDto leavesDto);

    LeavesDto update(Integer leavesID,LeavesDto leavesDto);


    List<LeavesDto> getAllLeavesOrderedByDate();

    List<LeavesDto> getAllLeavesByUsernameOrderedByDate(String username);

    void delete(Integer leavesID);
}
