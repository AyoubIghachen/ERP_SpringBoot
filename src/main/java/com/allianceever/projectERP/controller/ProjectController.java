package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.ProjectDto;
import com.allianceever.projectERP.service.EmployeeService;
import com.allianceever.projectERP.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/project")
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;
    private EmployeeService employeeService;

    // Build Get All Project REST API
    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        List<ProjectDto> projects = projectService.getAll();
        return ResponseEntity.ok(projects);
    }

    // Build Get Project REST API
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") Long projectID){
        ProjectDto projectDto = projectService.getById(projectID);
        if (projectDto != null) {
            return ResponseEntity.ok(projectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add Project REST API
    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(@ModelAttribute ProjectDto projectDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        if(role.equals("ADMIN")){
            projectDto.setCreated_By("ADMIN");
        }else{
            EmployeeDto employeeDto = employeeService.getByUsername(username);
            String employeeName = employeeDto.getFirst_Name() + " " + employeeDto.getLast_Name();
            projectDto.setCreated_By(employeeName);
        }

        ProjectDto createdProject = projectService.create(projectDto);
        return new ResponseEntity<>(createdProject, CREATED);
    }

    // Build Update Project REST API
    @PostMapping("/updateProject")
    public ResponseEntity<ProjectDto> updateProject(@ModelAttribute ProjectDto projectDto){
        Long ProjectID = projectDto.getProjectID();
        ProjectDto existingProject = projectService.getById(ProjectID);
        if (existingProject == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingProject using the projectDto data
        BeanUtils.copyProperties(projectDto, existingProject, getNullPropertyNames(projectDto));

        // Save the updated project data back to the database
        ProjectDto updatedProject = projectService.update(ProjectID,existingProject);
        return ResponseEntity.ok(updatedProject);
    }

    // Build Delete Project REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long projectID){
        ProjectDto projectDto = projectService.getById(projectID);
        if (projectDto != null) {
            projectService.delete(projectID);
            return ResponseEntity.ok("Project deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    // Helper method to get the names of null properties in the projectDto
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
