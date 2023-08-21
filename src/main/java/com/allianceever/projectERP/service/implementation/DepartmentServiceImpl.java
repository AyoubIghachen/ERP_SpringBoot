package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.DepartmentDto;
import com.allianceever.projectERP.model.entity.Department;
import com.allianceever.projectERP.repository.DepartmentRepo;
import com.allianceever.projectERP.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepo departmentRepo;
    private ModelMapper mapper;
    @Override
    public DepartmentDto create(DepartmentDto departmentDto) {
        // convert DTO to entity
        Department department = mapToEntity(departmentDto);
        Department newDepartment = departmentRepo.save(department);

        // convert entity to DTO
        return mapToDTO(newDepartment);
    }

    @Override
    public DepartmentDto update(Long DepartmentID, DepartmentDto departmentDto) {
        departmentRepo.findById(DepartmentID).orElseThrow(
                () -> new ResourceNotFoundException("Department is not exist with given id : " + DepartmentID)
        );
        // convert DTO to entity
        Department department = mapToEntity(departmentDto);
        Department updatedDepartment = departmentRepo.save(department);

        // convert entity to DTO
        return mapToDTO(updatedDepartment);
    }

    @Override
    public List<DepartmentDto> getAll() {
        List<Department> departments = departmentRepo.findAll();
        return departments.stream().map((department) -> mapToDTO(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getById(Long DepartmentID) {
        Department department = departmentRepo.findById(DepartmentID).orElseThrow(
                () -> new ResourceNotFoundException("Department is not exist with given id : " + DepartmentID));

        return mapToDTO(department);
    }

    @Override
    public void delete(Long DepartmentID) {
        Department department = departmentRepo.findById(DepartmentID).orElseThrow(
                () -> new ResourceNotFoundException("Department is not exist with given id : " + DepartmentID));

        departmentRepo.deleteById(DepartmentID);
    }




    // convert entity into DTO
    private DepartmentDto mapToDTO(Department department){
        DepartmentDto departmentDto = mapper.map(department, DepartmentDto.class);
        return departmentDto;
    }

    // convert DTO to entity
    private Department mapToEntity(DepartmentDto departmentDto){
        Department department = mapper.map(departmentDto, Department.class);
        return department;
    }
}
