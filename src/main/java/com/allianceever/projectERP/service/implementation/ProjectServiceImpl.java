package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.EmployeeProjectDto;
import com.allianceever.projectERP.model.dto.LeaderProjectDto;
import com.allianceever.projectERP.model.dto.ProjectDto;
import com.allianceever.projectERP.model.entity.Project;
import com.allianceever.projectERP.repository.ProjectRepo;
import com.allianceever.projectERP.service.EmployeeProjectService;
import com.allianceever.projectERP.service.EmployeeService;
import com.allianceever.projectERP.service.LeaderProjectService;
import com.allianceever.projectERP.service.ProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepo projectRepo;
    private ModelMapper mapper;
    private EmployeeService employeeService;
    private EmployeeProjectService employeeProjectService;
    private LeaderProjectService leaderProjectService;
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
    public List<ProjectDto> getAllByUsername(String username) {
        EmployeeDto employeeDto = employeeService.getByUsername(username);
        Long employeeID = employeeDto.getEmployeeID();
        List<EmployeeProjectDto> employeeProjectDtoList = employeeProjectService.findAllByEmployeeID(String.valueOf(employeeID));
        List<LeaderProjectDto> leaderProjectDtoList = leaderProjectService.findAllByLeaderID(String.valueOf(employeeID));

        // Create a Set to store unique project IDs
        Set<String> uniqueProjectIDs = new HashSet<>();
        // Iterate through the employeeProjectDtoList and add project IDs to the set
        for (EmployeeProjectDto employeeProjectDto : employeeProjectDtoList) {
            uniqueProjectIDs.add(employeeProjectDto.getProjectID());
        }
        // Iterate through the leaderProjectDtoList and add project IDs to the set
        for (LeaderProjectDto leaderProjectDto : leaderProjectDtoList) {
            uniqueProjectIDs.add(leaderProjectDto.getProjectID());
        }
        // Convert the set back to a list if needed
        List<String> uniqueProjectIDList = new ArrayList<>(uniqueProjectIDs);
        // Now, uniqueProjectIDList contains the unique project IDs from both lists

        List<Project> projects = new ArrayList<>();
        for (String projectID : uniqueProjectIDList) {
            projects.add(projectRepo.findById(Long.valueOf(projectID)).orElseThrow(
                    () -> new ResourceNotFoundException("Project is not exist with given id : " + projectID)));
        }

        return projects.stream().map((project) -> mapToDTO(project))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> getAllByCompany_Name(String company_Name) {
        List<Project> projects = projectRepo.findByCompany_Name(company_Name);
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
