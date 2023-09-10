package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto employeeDto);
    EmployeeDto update(Long EmployeeID, EmployeeDto employeeDto);
    List<EmployeeDto> getAll();
    EmployeeDto getById(Long EmployeeID);
    EmployeeDto getByEmail(String Email);
    void delete(Long EmployeeID);
    List<EmployeeDto> findByFirst_Name(String first_Name);

    EmployeeDto getByUsername(String username);
}
