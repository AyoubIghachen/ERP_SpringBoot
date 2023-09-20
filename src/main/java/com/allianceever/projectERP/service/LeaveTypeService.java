package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.LeaveTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LeaveTypeService {
    LeaveTypeDto getByLeaveName(String holidayName);

    LeaveTypeDto create(LeaveTypeDto leaveTypeDto);

    LeaveTypeDto update(String LeaveName, LeaveTypeDto leaveTypeDto) ;


    List<LeaveTypeDto> getAllLeaveType();

    List<LeaveTypeDto> getAllLeaveTypeByUsername(String username);

    void delete(String LeaveName);
}
