package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.ImageProjectDto;

import java.util.List;

public interface ImageProjectService {
    ImageProjectDto create(ImageProjectDto imageProjectDto);
    List<ImageProjectDto> getAll();
    List<ImageProjectDto> findAll(String projectID);
    ImageProjectDto getById(Long imageProjectID);
    void delete(Long imageProjectID);
}
