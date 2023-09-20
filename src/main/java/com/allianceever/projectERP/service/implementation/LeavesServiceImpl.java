package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.dto.LeavesDto;
import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.model.entity.Leaves;
import com.allianceever.projectERP.repository.HolidayRepo;
import com.allianceever.projectERP.repository.LeavesRepo;
import com.allianceever.projectERP.service.LeavesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeavesServiceImpl implements LeavesService {



    private LeavesRepo leavesRepo;
    private ModelMapper mapper;

    @Autowired
    public LeavesServiceImpl(LeavesRepo leavesRepo, ModelMapper mapper) {
        this.leavesRepo = leavesRepo;
        this.mapper = mapper;
    }

    @Override
    public LeavesDto getByLeavesID(Integer LeavesID) {
        Leaves leaves = leavesRepo.findByLeavesID(LeavesID);
        return mapper.map(leaves,LeavesDto.class);
    }



    @Override
    public LeavesDto create(LeavesDto holidayDto) {
        // convert DTO to entity
        Leaves leaves = mapper.map(holidayDto, Leaves.class);
        Leaves newHoliday = leavesRepo.save(leaves);

        // convert entity to DTO
        return mapper.map(newHoliday, LeavesDto.class);
    }

    @Override
    public LeavesDto update(Integer leavesID,LeavesDto leavesDto) {
        // Find the existing holiday entity by name
        Optional<Leaves> leavesOptional = Optional.ofNullable(leavesRepo.findByLeavesID(leavesID));

        Leaves existingLeaves = leavesOptional.orElseThrow(() ->
                new ResourceNotFoundException("Leaves does not exist with the given ID: " + leavesID)
        );


        // Update the fields of existingHoliday with the corresponding fields from holidayDto
        existingLeaves.setLeaveReason(leavesDto.getLeaveReason());
        existingLeaves.setLeaveType(leavesDto.getLeaveType());
        existingLeaves.setStartDate(leavesDto.getStartDate());
        existingLeaves.setEndDate(leavesDto.getEndDate());
        existingLeaves.setLeaveReason(leavesDto.getLeaveReason());
        existingLeaves.setStatus(leavesDto.getStatus());
        existingLeaves.setNumberOfDays(leavesDto.getNumberOfDays());




        // Save the updated entity back to the database
        Leaves updatedLeaves = leavesRepo.save(existingLeaves);


        // Convert the updated entity to DTO and return it
        return mapper.map(updatedLeaves, LeavesDto.class);
    }






    @Override
    public List<LeavesDto> getAllLeavesOrderedByDate() {
        List<Leaves> leavesList = leavesRepo.findAllOrderByDate();

        return leavesList.stream()
                .map(leaves -> mapper.map(leaves, LeavesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional

    public void delete(Integer LeavesID) {
        Optional<Leaves> LeavesOptional = Optional.ofNullable(leavesRepo.findByLeavesID(LeavesID));

        if (LeavesOptional.isPresent()) {
            Leaves holiday = LeavesOptional.get();
            leavesRepo.deleteByLeavesID(LeavesID);
        } else {
            throw new ResourceNotFoundException("Leaves is not exist with given ID: " + LeavesID);
        }
    }

    @Override
    public List<LeavesDto> getAllLeavesByUsernameOrderedByDate(String username) {
        List<Leaves> leavesList = leavesRepo.getAllLeavesByUsernameOrderedByDate(username);

        return leavesList.stream()
                .map(leaves -> mapper.map(leaves, LeavesDto.class))
                .collect(Collectors.toList());
    }
}
