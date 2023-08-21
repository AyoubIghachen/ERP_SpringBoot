package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.repository.HolidayRepo;
import com.allianceever.projectERP.service.HolidayService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class HolidayServiceImpl implements HolidayService {

    private HolidayRepo holidayRepo;
    private ModelMapper mapper;


    @Override
    public HolidayDto getByHolidayName(String holidayName) {
        Holiday holiday = holidayRepo.findByHolidayName(holidayName);
        return mapper.map(holiday,HolidayDto.class);
    }



    @Override
    public HolidayDto create(HolidayDto holidayDto) {
        // convert DTO to entity
        Holiday holiday = mapper.map(holidayDto, Holiday.class);
        Holiday newHoliday = holidayRepo.save(holiday);

        // convert entity to DTO
        return mapper.map(newHoliday, HolidayDto.class);
    }

    @Override
    public HolidayDto update(String holidayName, HolidayDto holidayDto) {
        // Find the existing holiday entity by name
        Optional<Holiday> holidayOptional = Optional.ofNullable(holidayRepo.findByHolidayName(holidayName));

        Holiday existingHoliday = holidayOptional.orElseThrow(() ->
                new ResourceNotFoundException("Holiday does not exist with the given name: " + holidayName)
        );


        // Update the fields of existingHoliday with the corresponding fields from holidayDto
        existingHoliday.setHolidayDate(holidayDto.getHolidayDate());
        existingHoliday.setHolidayDateEnd(holidayDto.getHolidayDateEnd());


        // Save the updated entity back to the database
        Holiday updatedHoliday = holidayRepo.save(existingHoliday);


        // Convert the updated entity to DTO and return it
        return mapper.map(updatedHoliday, HolidayDto.class);
    }






    @Override
    public List<HolidayDto> getAllHolidaysOrderedByDate() {
        List<Holiday> holidays = holidayRepo.findAllOrderByDate();

        return holidays.stream()
                .map(holiday -> mapper.map(holiday, HolidayDto.class))
                .collect(Collectors.toList());
}

    @Override
    @Transactional

    public void delete(String HolidayName) {
         Optional<Holiday> holidayOptional = Optional.ofNullable(holidayRepo.findByHolidayName(HolidayName));

        if (holidayOptional.isPresent()) {
            Holiday holiday = holidayOptional.get();
            holidayRepo.deleteByHolidayName(HolidayName);
        } else {
            throw new ResourceNotFoundException("Employee is not exist with given name: " + HolidayName);
        }
    }

}
