package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.TaskDto;
import com.allianceever.projectERP.model.entity.Task;
import com.allianceever.projectERP.repository.TaskRepo;
import com.allianceever.projectERP.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private TaskRepo taskRepo;
    private ModelMapper mapper;
    @Override
    public TaskDto create(TaskDto taskDto) {
        // convert DTO to entity
        Task task = mapToEntity(taskDto);
        Task newTask = taskRepo.save(task);

        // convert entity to DTO
        return mapToDTO(newTask);
    }

    @Override
    public TaskDto update(Long taskID, TaskDto taskDto) {
        taskRepo.findById(taskID).orElseThrow(
                () -> new ResourceNotFoundException("Task is not exist with given id : " + taskID)
        );
        // convert DTO to entity
        Task task = mapToEntity(taskDto);
        Task updatedTask = taskRepo.save(task);

        // convert entity to DTO
        return mapToDTO(updatedTask);
    }

    @Override
    public List<TaskDto> getAll() {
        List<Task> tasks = taskRepo.findAll();
        return tasks.stream().map((task) -> mapToDTO(task))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findAll(String projectID) {
        List<Task> tasks = taskRepo.findByProjectID(projectID);
        return tasks.stream().map((task) -> mapToDTO(task))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getById(Long TaskID) {
        Task task = taskRepo.findById(TaskID).orElseThrow(
                () -> new ResourceNotFoundException("Task is not exist with given id : " + TaskID));

        return mapToDTO(task);
    }


    @Override
    public void delete(Long TaskID) {
        Task task = taskRepo.findById(TaskID).orElseThrow(
                () -> new ResourceNotFoundException("Task is not exist with given id : " + TaskID));

        taskRepo.deleteById(TaskID);
    }




    // convert entity into DTO
    private TaskDto mapToDTO(Task task){
        TaskDto taskDto = mapper.map(task, TaskDto.class);
        return taskDto;
    }

    // convert DTO to entity
    private Task mapToEntity(TaskDto taskDto){
        Task task = mapper.map(taskDto, Task.class);
        return task;
    }
}
