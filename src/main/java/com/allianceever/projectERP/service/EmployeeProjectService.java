package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.EmployeeProjectDto;

import java.util.List;

public interface EmployeeProjectService {
    EmployeeProjectDto create(EmployeeProjectDto employeeProjectDto);
    List<EmployeeProjectDto> getAll();
    List<EmployeeProjectDto> findAll(String projectID);
    List<EmployeeProjectDto> findAllByEmployeeID(String employeeID);
    EmployeeProjectDto getById(Long employeeProjectID);
    EmployeeProjectDto getByEmployeeIDAndProjectID(String employeeID, String projectID);
    void delete(Long employeeProjectID);
}
