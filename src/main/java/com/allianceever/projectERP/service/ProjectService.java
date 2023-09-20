package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto create(ProjectDto projectDto);
    ProjectDto update(Long projectID, ProjectDto projectDto);
    List<ProjectDto> getAll();
    List<ProjectDto> getAllByUsername(String username);
    ProjectDto getById(Long projectID);
    void delete(Long projectID);
}
