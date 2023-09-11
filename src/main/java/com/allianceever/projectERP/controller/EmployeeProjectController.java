package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeProjectDto;
import com.allianceever.projectERP.service.EmployeeProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/employeeProject")
@AllArgsConstructor
public class EmployeeProjectController {
    private EmployeeProjectService employeeProjectService;

    // Build Get All EmployeeProject REST API
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeProjectDto>> getAllEmployeeProjects(){
        List<EmployeeProjectDto> employeeProjects = employeeProjectService.getAll();
        return ResponseEntity.ok(employeeProjects);
    }

    // Build Get EmployeeProject REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjectDto> getEmployeeProjectById(@PathVariable("id") Long employeeProjectID){
        EmployeeProjectDto employeeProjectDto = employeeProjectService.getById(employeeProjectID);
        if (employeeProjectDto != null) {
            return ResponseEntity.ok(employeeProjectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get EmployeeProject By employeeID And projectID REST API
    @GetMapping("/ByEmployeeIDAndProjectID")
    public ResponseEntity<EmployeeProjectDto> getEmployeeProjectByEmployeeIDAndProjectID(@RequestParam("employeeID") String employeeID, @RequestParam("projectID") String projectID){
        EmployeeProjectDto existeEmployeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID, projectID);
        if (existeEmployeeProjectDto != null) {
            return ResponseEntity.ok(existeEmployeeProjectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add EmployeeProject REST API
    @PostMapping("/create")
    public ResponseEntity<EmployeeProjectDto> createEmployeeProject(@ModelAttribute EmployeeProjectDto employeeProjectDto){
        EmployeeProjectDto createdEmployeeProject = employeeProjectService.create(employeeProjectDto);
        return new ResponseEntity<>(createdEmployeeProject, CREATED);
    }


    // Build Delete EmployeeProject REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeProject(@PathVariable("id") Long employeeProjectID){
        EmployeeProjectDto employeeProjectDto = employeeProjectService.getById(employeeProjectID);
        if (employeeProjectDto != null) {
            employeeProjectService.delete(employeeProjectID);
            return ResponseEntity.ok("EmployeeProject deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
