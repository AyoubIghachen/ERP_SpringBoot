package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.*;
import com.allianceever.projectERP.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/employeeTask")
@AllArgsConstructor
public class EmployeeTaskController {
    private EmployeeTaskService employeeTaskService;
    private LeaderProjectService leaderProjectService;
    private EmployeeService employeeService;
    private EmployeeProjectService employeeProjectService;
    private TaskService taskService;

    // Build Get All EmployeeTask REST API
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeTaskDto>> getAllEmployeeTasks(){
        List<EmployeeTaskDto> employeeTasks = employeeTaskService.getAll();
        return ResponseEntity.ok(employeeTasks);
    }

    // Build Get EmployeeTask REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTaskDto> getEmployeeTaskById(@PathVariable("id") Long employeeTaskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        EmployeeTaskDto employeeTaskDto = employeeTaskService.getById(employeeTaskID);
        if (employeeTaskDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                return ResponseEntity.ok(employeeTaskDto);
            }else {
                String taskID = employeeTaskDto.getTaskID();
                TaskDto taskDto = taskService.getById(Long.valueOf(taskID));
                String projectID = taskDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                EmployeeProjectDto employeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID,projectID);
                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null || employeeProjectDto != null){
                    return ResponseEntity.ok(employeeTaskDto);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get EmployeeTask By employeeID And taskID REST API
    @GetMapping("/ByEmployeeIDAndTaskID")
    public ResponseEntity<EmployeeTaskDto> getEmployeeTaskByEmployeeIDAndTaskID(@RequestParam("employeeID") String employeeID, @RequestParam("taskID") String taskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve role from the jwt Token
        String role = jwt.getClaimAsString("roles");

        EmployeeTaskDto existeEmployeeTaskDto = employeeTaskService.getByEmployeeIDAndTaskID(employeeID, taskID);
        if (existeEmployeeTaskDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                return ResponseEntity.ok(existeEmployeeTaskDto);
            }else {
                TaskDto taskDto = taskService.getById(Long.valueOf(taskID));
                String projectID = taskDto.getProjectID();

                EmployeeProjectDto employeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID,projectID);
                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null || employeeProjectDto != null){
                    return ResponseEntity.ok(existeEmployeeTaskDto);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add EmployeeTask REST API
    @PostMapping("/create")
    public ResponseEntity<EmployeeTaskDto> createEmployeeTask(@ModelAttribute EmployeeTaskDto employeeTaskDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        if(role.equals("ADMIN") || role.equals("Business_Development")){
            EmployeeTaskDto createdEmployeeTask = employeeTaskService.create(employeeTaskDto);
            return new ResponseEntity<>(createdEmployeeTask, CREATED);
        }else {
            String taskID = employeeTaskDto.getTaskID();
            TaskDto taskDto = taskService.getById(Long.valueOf(taskID));
            String projectID = taskDto.getProjectID();
            EmployeeDto employeeDto = employeeService.getByUsername(username);
            String employeeID = String.valueOf(employeeDto.getEmployeeID());

            LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
            if(leaderProjectDto != null){
                EmployeeTaskDto createdEmployeeTask = employeeTaskService.create(employeeTaskDto);
                return new ResponseEntity<>(createdEmployeeTask, CREATED);
            }
            return ResponseEntity.notFound().build();
        }
    }


    // Build Delete EmployeeTask REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeTask(@PathVariable("id") Long employeeTaskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        EmployeeTaskDto employeeTaskDto = employeeTaskService.getById(employeeTaskID);
        if (employeeTaskDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                employeeTaskService.delete(employeeTaskID);
                return ResponseEntity.ok("EmployeeTask deleted successfully!");
            }else {
                String taskID = employeeTaskDto.getTaskID();
                TaskDto taskDto = taskService.getById(Long.valueOf(taskID));
                String projectID = taskDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    employeeTaskService.delete(employeeTaskID);
                    return ResponseEntity.ok("EmployeeTask deleted successfully!");
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get All EmployeeTask By TaskID REST API
    @GetMapping("/ByTaskID/{taskID}")
    public ResponseEntity<List<EmployeeTaskDto>> getAllEmployeeTasksByTaskID(@PathVariable("taskID") String taskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        if(role.equals("ADMIN") || role.equals("Business_Development")){
            List<EmployeeTaskDto> employeeTasks = employeeTaskService.findAll(taskID);
            return ResponseEntity.ok(employeeTasks);
        }else {
            TaskDto taskDto = taskService.getById(Long.valueOf(taskID));
            String projectID = taskDto.getProjectID();
            EmployeeDto employeeDto = employeeService.getByUsername(username);
            String employeeID = String.valueOf(employeeDto.getEmployeeID());

            EmployeeProjectDto employeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID,projectID);
            LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
            if(leaderProjectDto != null || employeeProjectDto != null){
                List<EmployeeTaskDto> employeeTasks = employeeTaskService.findAll(taskID);
                return ResponseEntity.ok(employeeTasks);
            }
            return ResponseEntity.notFound().build();
        }
    }

}
