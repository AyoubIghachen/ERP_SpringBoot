package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.EmployeeTaskDto;
import com.allianceever.projectERP.model.entity.EmployeeTask;
import com.allianceever.projectERP.repository.EmployeeTaskRepo;
import com.allianceever.projectERP.service.EmployeeTaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeTaskServiceImpl implements EmployeeTaskService {
    private EmployeeTaskRepo employeeTaskRepo;
    private ModelMapper mapper;
    @Override
    public EmployeeTaskDto create(EmployeeTaskDto employeeTaskDto) {
        // convert DTO to entity
        EmployeeTask employeeTask = mapToEntity(employeeTaskDto);
        EmployeeTask newEmployeeTask = employeeTaskRepo.save(employeeTask);

        // convert entity to DTO
        return mapToDTO(newEmployeeTask);
    }

    @Override
    public List<EmployeeTaskDto> getAll() {
        List<EmployeeTask> employeeTasks = employeeTaskRepo.findAll();
        return employeeTasks.stream().map((employeeTask) -> mapToDTO(employeeTask))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeTaskDto> findAll(String taskID) {
        List<EmployeeTask> employeeTasks = employeeTaskRepo.findByTaskID(taskID);
        return employeeTasks.stream().map((employeeTask) -> mapToDTO(employeeTask))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeTaskDto getById(Long EmployeeTaskID) {
        EmployeeTask employeeTask = employeeTaskRepo.findById(EmployeeTaskID).orElseThrow(
                () -> new ResourceNotFoundException("EmployeeTask is not exist with given id : " + EmployeeTaskID));

        return mapToDTO(employeeTask);
    }

    @Override
    public EmployeeTaskDto getByEmployeeIDAndTaskID(String employeeID, String taskID) {
        EmployeeTask employeeTask = employeeTaskRepo.findByEmployeeIDAndTaskID(employeeID, taskID);
        if(employeeTask != null){
            return mapToDTO(employeeTask);
        }else{
            return null;
        }
    }

    @Override
    public void delete(Long EmployeeTaskID) {
        EmployeeTask employeeTask = employeeTaskRepo.findById(EmployeeTaskID).orElseThrow(
                () -> new ResourceNotFoundException("EmployeeTask is not exist with given id : " + EmployeeTaskID));

        employeeTaskRepo.deleteById(EmployeeTaskID);
    }




    // convert entity into DTO
    private EmployeeTaskDto mapToDTO(EmployeeTask employeeTask){
        EmployeeTaskDto employeeTaskDto = mapper.map(employeeTask, EmployeeTaskDto.class);
        return employeeTaskDto;
    }

    // convert DTO to entity
    private EmployeeTask mapToEntity(EmployeeTaskDto employeeTaskDto){
        EmployeeTask employeeTask = mapper.map(employeeTaskDto, EmployeeTask.class);
        return employeeTask;
    }
}
