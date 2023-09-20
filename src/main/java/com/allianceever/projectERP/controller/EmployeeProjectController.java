package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.EmployeeProjectDto;
import com.allianceever.projectERP.model.dto.FileProjectDto;
import com.allianceever.projectERP.model.dto.LeaderProjectDto;
import com.allianceever.projectERP.service.EmployeeProjectService;
import com.allianceever.projectERP.service.EmployeeService;
import com.allianceever.projectERP.service.LeaderProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/employeeProject")
@AllArgsConstructor
public class EmployeeProjectController {
    private EmployeeProjectService employeeProjectService;
    private LeaderProjectService leaderProjectService;
    private EmployeeService employeeService;

    // Build Get All EmployeeProject REST API
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeProjectDto>> getAllEmployeeProjects(){
        List<EmployeeProjectDto> employeeProjects = employeeProjectService.getAll();
        return ResponseEntity.ok(employeeProjects);
    }

    // Build Get EmployeeProject REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjectDto> getEmployeeProjectById(@PathVariable("id") Long employeeProjectID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        EmployeeProjectDto employeeProjectDto = employeeProjectService.getById(employeeProjectID);
        if (employeeProjectDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                return ResponseEntity.ok(employeeProjectDto);
            }else {
                String projectID = employeeProjectDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    return ResponseEntity.ok(employeeProjectDto);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get EmployeeProject By employeeID And projectID REST API
    @GetMapping("/ByEmployeeIDAndProjectID")
    public ResponseEntity<EmployeeProjectDto> getEmployeeProjectByEmployeeIDAndProjectID(@RequestParam("employeeID") String employeeID, @RequestParam("projectID") String projectID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve role from the jwt Token
        String role = jwt.getClaimAsString("roles");

        EmployeeProjectDto existeEmployeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID, projectID);
        if (existeEmployeeProjectDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                return ResponseEntity.ok(existeEmployeeProjectDto);
            }else {
                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    return ResponseEntity.ok(existeEmployeeProjectDto);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add EmployeeProject REST API
    @PostMapping("/create")
    public ResponseEntity<EmployeeProjectDto> createEmployeeProject(@ModelAttribute EmployeeProjectDto employeeProjectDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        if(role.equals("ADMIN") || role.equals("Business_Development")){
            EmployeeProjectDto createdEmployeeProject = employeeProjectService.create(employeeProjectDto);
            return new ResponseEntity<>(createdEmployeeProject, CREATED);
        }else {
            String projectID = employeeProjectDto.getProjectID();
            EmployeeDto employeeDto = employeeService.getByUsername(username);
            String employeeID = String.valueOf(employeeDto.getEmployeeID());

            LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
            if(leaderProjectDto != null){
                EmployeeProjectDto createdEmployeeProject = employeeProjectService.create(employeeProjectDto);
                return new ResponseEntity<>(createdEmployeeProject, CREATED);
            }
            return ResponseEntity.notFound().build();
        }
    }


    // Build Delete EmployeeProject REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeProject(@PathVariable("id") Long employeeProjectID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        EmployeeProjectDto employeeProjectDto = employeeProjectService.getById(employeeProjectID);
        if (employeeProjectDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                employeeProjectService.delete(employeeProjectID);
                return ResponseEntity.ok("EmployeeProject deleted successfully!");
            }else {
                String projectID = employeeProjectDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    employeeProjectService.delete(employeeProjectID);
                    return ResponseEntity.ok("EmployeeProject deleted successfully!");
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
