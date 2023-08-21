package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto create(DepartmentDto departmentDto);
    DepartmentDto update(Long departmentID, DepartmentDto departmentDto);
    List<DepartmentDto> getAll();
    DepartmentDto getById(Long departmentID);
    void delete(Long departmentID);
}
