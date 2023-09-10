package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.entity.Employee;
import com.allianceever.projectERP.repository.EmployeeRepo;
import com.allianceever.projectERP.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepo employeeRepo;
    private ModelMapper mapper;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo, ModelMapper mapper) {
        this.employeeRepo = employeeRepo;
        this.mapper = mapper;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        // convert DTO to entity
        Employee employee = mapToEntity(employeeDto);

        Employee newEmployee = employeeRepo.save(employee);

        // convert entity to DTO
        return mapToDTO(newEmployee);
    }

    @Override
    public EmployeeDto update(Long EmployeeID, EmployeeDto employeeDto) {
        employeeRepo.findById(EmployeeID).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with given id : " + EmployeeID)
        );
        // convert DTO to entity
        Employee employee = mapToEntity(employeeDto);
        Employee updatedEmployee = employeeRepo.save(employee);

        // convert entity to DTO
        return mapToDTO(updatedEmployee);
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().map((employee) -> mapToDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getById(Long EmployeeID) {
        Employee employee = employeeRepo.findById(EmployeeID).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with given id : " + EmployeeID));

        return mapToDTO(employee);
    }

    @Override
    public EmployeeDto getByEmail(String Email) {
        Employee employee = employeeRepo.findByEmail(Email);
        return mapToDTO(employee);
    }

    @Override
    public void delete(Long EmployeeID) {
        Employee employee = employeeRepo.findById(EmployeeID).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with given id : " + EmployeeID));

        employeeRepo.deleteById(EmployeeID);
    }

    @Override
    public List<EmployeeDto> findByFirst_Name(String first_Name) {
        List<Employee> employees = employeeRepo.findByFirst_NameLikeIgnoreCase(first_Name);
        return employees.stream().map((employee) -> mapToDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getByUsername(String username) {
        Employee employee = employeeRepo.findByUserName(username);
        return mapToDTO(employee);
    }



    // convert entity into DTO
    private EmployeeDto mapToDTO(Employee employee){
        EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }

    // convert DTO to entity
    private Employee mapToEntity(EmployeeDto employeeDto){
        Employee employee = mapper.map(employeeDto, Employee.class);
        return employee;
    }
}
