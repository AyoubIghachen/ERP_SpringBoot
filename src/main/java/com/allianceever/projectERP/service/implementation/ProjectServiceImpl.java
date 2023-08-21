package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.ProjectDto;
import com.allianceever.projectERP.model.entity.Project;
import com.allianceever.projectERP.repository.ProjectRepo;
import com.allianceever.projectERP.service.ProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepo projectRepo;
    private ModelMapper mapper;
    @Override
    public ProjectDto create(ProjectDto projectDto) {
        // convert DTO to entity
        Project project = mapToEntity(projectDto);
        Project newProject = projectRepo.save(project);

        // convert entity to DTO
        return mapToDTO(newProject);
    }

    @Override
    public ProjectDto update(Long ProjectID, ProjectDto projectDto) {
        projectRepo.findById(ProjectID).orElseThrow(
                () -> new ResourceNotFoundException("Project is not exist with given id : " + ProjectID)
        );
        // convert DTO to entity
        Project project = mapToEntity(projectDto);
        Project updatedProject = projectRepo.save(project);

        // convert entity to DTO
        return mapToDTO(updatedProject);
    }

    @Override
    public List<ProjectDto> getAll() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream().map((project) -> mapToDTO(project))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getById(Long ProjectID) {
        Project project = projectRepo.findById(ProjectID).orElseThrow(
                () -> new ResourceNotFoundException("Project is not exist with given id : " + ProjectID));

        return mapToDTO(project);
    }

    @Override
    public void delete(Long ProjectID) {
        Project project = projectRepo.findById(ProjectID).orElseThrow(
                () -> new ResourceNotFoundException("Project is not exist with given id : " + ProjectID));

        projectRepo.deleteById(ProjectID);
    }




    // convert entity into DTO
    private ProjectDto mapToDTO(Project project){
        ProjectDto projectDto = mapper.map(project, ProjectDto.class);
        return projectDto;
    }

    // convert DTO to entity
    private Project mapToEntity(ProjectDto projectDto){
        Project project = mapper.map(projectDto, Project.class);
        return project;
    }
}
