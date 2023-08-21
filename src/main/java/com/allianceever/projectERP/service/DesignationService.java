package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.DesignationDto;

import java.util.List;

public interface DesignationService {
    DesignationDto create(DesignationDto designationDto);
    DesignationDto update(Long designationID, DesignationDto designationDto);
    List<DesignationDto> getAll();
    DesignationDto getById(Long designationID);
    void delete(Long designationID);
}
