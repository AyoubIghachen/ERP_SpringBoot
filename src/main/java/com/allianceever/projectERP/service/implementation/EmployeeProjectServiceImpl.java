package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.EmployeeProjectDto;
import com.allianceever.projectERP.model.entity.EmployeeProject;
import com.allianceever.projectERP.repository.EmployeeProjectRepo;
import com.allianceever.projectERP.service.EmployeeProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeProjectServiceImpl implements EmployeeProjectService {
    private EmployeeProjectRepo employeeProjectRepo;
    private ModelMapper mapper;
    @Override
    public EmployeeProjectDto create(EmployeeProjectDto employeeProjectDto) {
        // convert DTO to entity
        EmployeeProject employeeProject = mapToEntity(employeeProjectDto);
        EmployeeProject newEmployeeProject = employeeProjectRepo.save(employeeProject);

        // convert entity to DTO
        return mapToDTO(newEmployeeProject);
    }

    @Override
    public List<EmployeeProjectDto> getAll() {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findAll();
        return employeeProjects.stream().map((employeeProject) -> mapToDTO(employeeProject))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProjectDto> findAll(String projectID) {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findByProjectID(projectID);
        return employeeProjects.stream().map((employeeProject) -> mapToDTO(employeeProject))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProjectDto> findAllByEmployeeID(String employeeID) {
        List<EmployeeProject> employeeProjects = employeeProjectRepo.findByEmployeeID(employeeID);
        return employeeProjects.stream().map((employeeProject) -> mapToDTO(employeeProject))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeProjectDto getById(Long EmployeeProjectID) {
        EmployeeProject employeeProject = employeeProjectRepo.findById(EmployeeProjectID).orElseThrow(
                () -> new ResourceNotFoundException("EmployeeProject is not exist with given id : " + EmployeeProjectID));

        return mapToDTO(employeeProject);
    }

    @Override
    public EmployeeProjectDto getByEmployeeIDAndProjectID(String employeeID, String projectID) {
        EmployeeProject employeeProject = employeeProjectRepo.findByEmployeeIDAndProjectID(employeeID, projectID);
        if(employeeProject != null){
            return mapToDTO(employeeProject);
        }else{
            return null;
        }
    }

    @Override
    public void delete(Long EmployeeProjectID) {
        EmployeeProject employeeProject = employeeProjectRepo.findById(EmployeeProjectID).orElseThrow(
                () -> new ResourceNotFoundException("EmployeeProject is not exist with given id : " + EmployeeProjectID));

        employeeProjectRepo.deleteById(EmployeeProjectID);
    }




    // convert entity into DTO
    private EmployeeProjectDto mapToDTO(EmployeeProject employeeProject){
        EmployeeProjectDto employeeProjectDto = mapper.map(employeeProject, EmployeeProjectDto.class);
        return employeeProjectDto;
    }

    // convert DTO to entity
    private EmployeeProject mapToEntity(EmployeeProjectDto employeeProjectDto){
        EmployeeProject employeeProject = mapper.map(employeeProjectDto, EmployeeProject.class);
        return employeeProject;
    }
}
