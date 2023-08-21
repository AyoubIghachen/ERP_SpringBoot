package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.DesignationDto;
import com.allianceever.projectERP.model.entity.Designation;
import com.allianceever.projectERP.repository.DesignationRepo;
import com.allianceever.projectERP.service.DesignationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DesignationServiceImpl implements DesignationService {
    private DesignationRepo designationRepo;
    private ModelMapper mapper;
    @Override
    public DesignationDto create(DesignationDto designationDto) {
        // convert DTO to entity
        Designation designation = mapToEntity(designationDto);
        Designation newDesignation = designationRepo.save(designation);

        // convert entity to DTO
        return mapToDTO(newDesignation);
    }

    @Override
    public DesignationDto update(Long DesignationID, DesignationDto designationDto) {
        designationRepo.findById(DesignationID).orElseThrow(
                () -> new ResourceNotFoundException("Designation is not exist with given id : " + DesignationID)
        );
        // convert DTO to entity
        Designation designation = mapToEntity(designationDto);
        Designation updatedDesignation = designationRepo.save(designation);

        // convert entity to DTO
        return mapToDTO(updatedDesignation);
    }

    @Override
    public List<DesignationDto> getAll() {
        List<Designation> designations = designationRepo.findAll();
        return designations.stream().map((designation) -> mapToDTO(designation))
                .collect(Collectors.toList());
    }

    @Override
    public DesignationDto getById(Long DesignationID) {
        Designation designation = designationRepo.findById(DesignationID).orElseThrow(
                () -> new ResourceNotFoundException("Designation is not exist with given id : " + DesignationID));

        return mapToDTO(designation);
    }

    @Override
    public void delete(Long DesignationID) {
        Designation designation = designationRepo.findById(DesignationID).orElseThrow(
                () -> new ResourceNotFoundException("Designation is not exist with given id : " + DesignationID));

        designationRepo.deleteById(DesignationID);
    }




    // convert entity into DTO
    private DesignationDto mapToDTO(Designation designation){
        DesignationDto designationDto = mapper.map(designation, DesignationDto.class);
        return designationDto;
    }

    // convert DTO to entity
    private Designation mapToEntity(DesignationDto designationDto){
        Designation designation = mapper.map(designationDto, Designation.class);
        return designation;
    }
}
