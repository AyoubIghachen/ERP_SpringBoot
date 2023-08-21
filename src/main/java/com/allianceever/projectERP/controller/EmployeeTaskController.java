package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeTaskDto;
import com.allianceever.projectERP.service.EmployeeTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/employeeTask")
@AllArgsConstructor
public class EmployeeTaskController {
    private EmployeeTaskService employeeTaskService;

    // Build Get All EmployeeTask REST API
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeTaskDto>> getAllEmployeeTasks(){
        List<EmployeeTaskDto> employeeTasks = employeeTaskService.getAll();
        return ResponseEntity.ok(employeeTasks);
    }

    // Build Get EmployeeTask REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTaskDto> getEmployeeTaskById(@PathVariable("id") Long employeeTaskID){
        EmployeeTaskDto employeeTaskDto = employeeTaskService.getById(employeeTaskID);
        if (employeeTaskDto != null) {
            return ResponseEntity.ok(employeeTaskDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get EmployeeTask By employeeID And taskID REST API
    @GetMapping("/ByEmployeeIDAndTaskID")
    public ResponseEntity<EmployeeTaskDto> getEmployeeTaskByEmployeeIDAndTaskID(@RequestParam("employeeID") String employeeID, @RequestParam("taskID") String taskID){
        EmployeeTaskDto existeEmployeeTaskDto = employeeTaskService.getByEmployeeIDAndTaskID(employeeID, taskID);
        if (existeEmployeeTaskDto != null) {
            return ResponseEntity.ok(existeEmployeeTaskDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add EmployeeTask REST API
    @PostMapping("/create")
    public ResponseEntity<EmployeeTaskDto> createEmployeeTask(@ModelAttribute EmployeeTaskDto employeeTaskDto){
        EmployeeTaskDto createdEmployeeTask = employeeTaskService.create(employeeTaskDto);
        return new ResponseEntity<>(createdEmployeeTask, CREATED);
    }


    // Build Delete EmployeeTask REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeTask(@PathVariable("id") Long employeeTaskID){
        EmployeeTaskDto employeeTaskDto = employeeTaskService.getById(employeeTaskID);
        if (employeeTaskDto != null) {
            employeeTaskService.delete(employeeTaskID);
            return ResponseEntity.ok("EmployeeTask deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get All EmployeeTask By TaskID REST API
    @GetMapping("/ByTaskID/{taskID}")
    public ResponseEntity<List<EmployeeTaskDto>> getAllEmployeeTasksByTaskID(@PathVariable("taskID") String taskID){
        List<EmployeeTaskDto> employeeTasks = employeeTaskService.findAll(taskID);
        return ResponseEntity.ok(employeeTasks);
    }

}
