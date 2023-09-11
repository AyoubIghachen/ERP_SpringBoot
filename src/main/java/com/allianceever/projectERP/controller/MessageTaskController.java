package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.MessageTaskDto;
import com.allianceever.projectERP.service.EmployeeService;
import com.allianceever.projectERP.service.MessageTaskService;
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

    // Build Get All MessageTask REST API
    @GetMapping("/all")
    public ResponseEntity<List<MessageTaskDto>> getAllMessageTasks(){
        List<MessageTaskDto> messageTasks = messageTaskService.getAll();
        return ResponseEntity.ok(messageTasks);
    }

    // Build Get MessageTask REST API
    @GetMapping("/{id}")
    public ResponseEntity<MessageTaskDto> getMessageTaskById(@PathVariable("id") Long messageTaskID){
        MessageTaskDto messageTaskDto = messageTaskService.getById(messageTaskID);
        if (messageTaskDto != null) {
            return ResponseEntity.ok(messageTaskDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add MessageTask REST API
    @PostMapping("/create")
    public ResponseEntity<MessageTaskDto> createMessageTask(@ModelAttribute MessageTaskDto messageTaskDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username from the jwt
        String username = jwt.getClaimAsString("sub");
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

            MessageTaskDto createdMessageTask = messageTaskService.create(messageTaskDto);
            return new ResponseEntity<>(createdMessageTask, CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Build Delete MessageTask REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessageTask(@PathVariable("id") Long messageTaskID){
        MessageTaskDto messageTaskDto = messageTaskService.getById(messageTaskID);
        if (messageTaskDto != null) {
            messageTaskService.delete(messageTaskID);
            return ResponseEntity.ok("MessageTask deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get All MessageTask By TaskID REST API
    @GetMapping("/ByTaskID/{taskID}")
    public ResponseEntity<List<MessageTaskDto>> getAllMessageTasksByTaskID(@PathVariable("taskID") String taskID){
        List<MessageTaskDto> messageTasks = messageTaskService.findAll(taskID);
        return ResponseEntity.ok(messageTasks);
    }

}
