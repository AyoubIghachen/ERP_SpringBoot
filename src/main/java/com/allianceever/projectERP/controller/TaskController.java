package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.TaskDto;
import com.allianceever.projectERP.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private TaskService taskService;

    // Build Get All Task REST API
    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        List<TaskDto> tasks = taskService.getAll();
        return ResponseEntity.ok(tasks);
    }

    // Build Get Task REST API
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long taskID){
        TaskDto taskDto = taskService.getById(taskID);
        if (taskDto != null) {
            return ResponseEntity.ok(taskDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Build Add Task REST API
    @PostMapping("/create")
    public ResponseEntity<TaskDto> createTask(@ModelAttribute TaskDto taskDto){
        TaskDto createdTask = taskService.create(taskDto);
        return new ResponseEntity<>(createdTask, CREATED);
    }


    // Build Update Task REST API
    @PostMapping("/updateTask")
    public ResponseEntity<TaskDto> updateTask(@ModelAttribute TaskDto taskDto){
        Long TaskID = taskDto.getTaskID();
        TaskDto existingTask = taskService.getById(TaskID);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingTask using the taskDto data
        BeanUtils.copyProperties(taskDto, existingTask, getNullPropertyNames(taskDto));

        // Save the updated task data back to the database
        TaskDto updatedTask = taskService.update(TaskID,existingTask);
        return ResponseEntity.ok(updatedTask);
    }


    // Build Delete Task REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskID){
        TaskDto taskDto = taskService.getById(taskID);
        if (taskDto != null) {
            taskService.delete(taskID);
            return ResponseEntity.ok("Task deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // Helper method to get the names of null properties in the employeeDto
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
