package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.LeaveTypeDto;
import com.allianceever.projectERP.model.entity.LeaveType;
import com.allianceever.projectERP.repository.LeaveTypeRepo;
import com.allianceever.projectERP.service.LeaveTypeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

    private LeaveTypeRepo leaveTypeRepo;
    private ModelMapper mapper;


    @Autowired
    public LeaveTypeServiceImpl(LeaveTypeRepo leaveTypeRepo, ModelMapper mapper) {
        this.leaveTypeRepo = leaveTypeRepo;
        this.mapper = mapper;
    }

    @Override
    public LeaveTypeDto getByLeaveName(String leaveName) {
        LeaveType leaveType = leaveTypeRepo.findByLeaveName(leaveName);
        return mapper.map(leaveType,LeaveTypeDto.class);
    }

    @Override
    public LeaveTypeDto create(LeaveTypeDto leaveTypeDto) {
        LeaveType leaveType = mapper.map(leaveTypeDto, LeaveType.class);
        LeaveType newLeaveType = leaveTypeRepo.save(leaveType);
        // convert entity to DTO
        return mapper.map(newLeaveType, LeaveTypeDto.class);
    }

    @Override
    public LeaveTypeDto update(String LeaveName, LeaveTypeDto leaveTypeDto) {
// Find the existing holiday entity by name
        Optional<LeaveType> leaveTypeOptional = Optional.ofNullable(leaveTypeRepo.findByLeaveName(LeaveName));

        LeaveType existingLeaveType = leaveTypeOptional.orElseThrow(() ->
                new ResourceNotFoundException("Holiday does not exist with the given name: " + LeaveName)
        );


        // Update the fields of existingHoliday with the corresponding fields from holidayDto
        existingLeaveType.setLeaveStatus(leaveTypeDto.getLeaveStatus());
        existingLeaveType.setDays(leaveTypeDto.getDays());


        // Save the updated entity back to the database
        LeaveType updatedLeaveType = leaveTypeRepo.save(existingLeaveType);


        // Convert the updated entity to DTO and return it
        return mapper.map(updatedLeaveType, LeaveTypeDto.class);

    }

    @Override
    public List<LeaveTypeDto> getAllLeaveType() {
        List<LeaveType> leaveTypes = leaveTypeRepo.findAll();

        return leaveTypes.stream()
                .map(leaveType -> mapper.map(leaveType, LeaveTypeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String LeaveName) {
        Optional<LeaveType> leaveTypeOptional = Optional.ofNullable(leaveTypeRepo.findByLeaveName(LeaveName));

        if (leaveTypeOptional.isPresent()) {
            LeaveType leaveType = leaveTypeOptional.get();
            leaveTypeRepo.deleteByLeaveName(LeaveName);
        } else {
            throw new ResourceNotFoundException("Leave type is not exist with given name: " + LeaveName);
        }
    }

    @Override
    public List<LeaveTypeDto> getAllLeaveTypeByUsername(String username) {
        List<LeaveType> leaveTypes = leaveTypeRepo.getAllLeaveTypeByUsername(username);

        return leaveTypes.stream()
                .map(leaveType -> mapper.map(leaveType, LeaveTypeDto.class))
                .collect(Collectors.toList());
    }
}
