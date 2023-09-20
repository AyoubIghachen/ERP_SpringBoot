package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.*;
import com.allianceever.projectERP.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/messageTask")
@AllArgsConstructor
public class MessageTaskController {
    private MessageTaskService messageTaskService;
    private EmployeeService employeeService;
    private LeaderProjectService leaderProjectService;
    private EmployeeProjectService employeeProjectService;
    private TaskService taskService;
    private EmployeeTaskService employeeTaskService;

    // Build Get All MessageTask REST API
    @GetMapping("/all")
    public ResponseEntity<List<MessageTaskDto>> getAllMessageTasks(){
        List<MessageTaskDto> messageTasks = messageTaskService.getAll();
        return ResponseEntity.ok(messageTasks);
    }

    // Build Get MessageTask REST API
    @GetMapping("/{id}")
    public ResponseEntity<MessageTaskDto> getMessageTaskById(@PathVariable("id") Long messageTaskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        MessageTaskDto messageTaskDto = messageTaskService.getById(messageTaskID);
        if (messageTaskDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                return ResponseEntity.ok(messageTaskDto);
            }else {
                TaskDto taskDto = taskService.getById(Long.valueOf(messageTaskDto.getTaskID()));
                String projectID = taskDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                EmployeeTaskDto employeeTaskDto = employeeTaskService.getByEmployeeIDAndTaskID(employeeID,messageTaskDto.getTaskID());
                EmployeeProjectDto employeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID,projectID);
                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null || employeeProjectDto != null || employeeTaskDto != null){
                    return ResponseEntity.ok(messageTaskDto);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add MessageTask REST API
    @PostMapping("/create")
    public ResponseEntity<MessageTaskDto> createMessageTask(@ModelAttribute MessageTaskDto messageTaskDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        // Get employee by username
        EmployeeDto employeeDto = employeeService.getByUsername(username);

        if (employeeDto != null) {
            // Generate date for file:
            DateFormat date_format1 = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat date_format2 = new SimpleDateFormat("HH:mm");
            Date dateFile = new Date();
            String messageDate = date_format1.format(dateFile) + " at " + date_format2.format(dateFile);

            // Set messageTaskDto
            messageTaskDto.setEmployeeID(String.valueOf(employeeDto.getEmployeeID()));
            messageTaskDto.setFirst_Name(employeeDto.getFirst_Name());
            messageTaskDto.setLast_Name(employeeDto.getLast_Name());
            messageTaskDto.setImageName(employeeDto.getImageName());
            messageTaskDto.setDate(messageDate);

            if(role.equals("ADMIN") || role.equals("Business_Development")){
                MessageTaskDto createdMessageTask = messageTaskService.create(messageTaskDto);
                return new ResponseEntity<>(createdMessageTask, CREATED);
            }else {
                TaskDto taskDto = taskService.getById(Long.valueOf(messageTaskDto.getTaskID()));
                String projectID = taskDto.getProjectID();
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                EmployeeTaskDto employeeTaskDto = employeeTaskService.getByEmployeeIDAndTaskID(employeeID,messageTaskDto.getTaskID());
                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null || employeeTaskDto != null){
                    MessageTaskDto createdMessageTask = messageTaskService.create(messageTaskDto);
                    return new ResponseEntity<>(createdMessageTask, CREATED);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Build Delete MessageTask REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessageTask(@PathVariable("id") Long messageTaskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        MessageTaskDto messageTaskDto = messageTaskService.getById(messageTaskID);
        if (messageTaskDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                messageTaskService.delete(messageTaskID);
                return ResponseEntity.ok("MessageTask deleted successfully!");
            }else {
                TaskDto taskDto = taskService.getById(Long.valueOf(messageTaskDto.getTaskID()));
                String projectID = taskDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    messageTaskService.delete(messageTaskID);
                    return ResponseEntity.ok("MessageTask deleted successfully!");
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get All MessageTask By TaskID REST API
    @GetMapping("/ByTaskID/{taskID}")
    public ResponseEntity<List<MessageTaskDto>> getAllMessageTasksByTaskID(@PathVariable("taskID") String taskID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        if(role.equals("ADMIN") || role.equals("Business_Development")){
            List<MessageTaskDto> messageTasks = messageTaskService.findAll(taskID);
            return ResponseEntity.ok(messageTasks);
        }else {
            TaskDto taskDto = taskService.getById(Long.valueOf(taskID));
            String projectID = taskDto.getProjectID();
            EmployeeDto employeeDto = employeeService.getByUsername(username);
            String employeeID = String.valueOf(employeeDto.getEmployeeID());

            EmployeeTaskDto employeeTaskDto = employeeTaskService.getByEmployeeIDAndTaskID(employeeID,taskID);
            EmployeeProjectDto employeeProjectDto = employeeProjectService.getByEmployeeIDAndProjectID(employeeID,projectID);
            LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
            if(leaderProjectDto != null || employeeProjectDto != null || employeeTaskDto != null){
                List<MessageTaskDto> messageTasks = messageTaskService.findAll(taskID);
                return ResponseEntity.ok(messageTasks);
            }
            return ResponseEntity.notFound().build();
        }
    }

}
