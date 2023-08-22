package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.MessageTaskDto;
import com.allianceever.projectERP.model.entity.MessageTask;
import com.allianceever.projectERP.repository.MessageTaskRepo;
import com.allianceever.projectERP.service.MessageTaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageTaskServiceImpl implements MessageTaskService {
    private MessageTaskRepo messageTaskRepo;
    private ModelMapper mapper;
    @Override
    public MessageTaskDto create(MessageTaskDto messageTaskDto) {
        // convert DTO to entity
        MessageTask messageTask = mapToEntity(messageTaskDto);
        MessageTask newMessageTask = messageTaskRepo.save(messageTask);

        // convert entity to DTO
        return mapToDTO(newMessageTask);
    }

    @Override
    public List<MessageTaskDto> getAll() {
        List<MessageTask> messageTasks = messageTaskRepo.findAll();
        return messageTasks.stream().map((messageTask) -> mapToDTO(messageTask))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageTaskDto> findAll(String taskID) {
        List<MessageTask> messageTasks = messageTaskRepo.findByTaskID(taskID);
        return messageTasks.stream().map((messageTask) -> mapToDTO(messageTask))
                .collect(Collectors.toList());
    }

    @Override
    public MessageTaskDto getById(Long MessageTaskID) {
        MessageTask messageTask = messageTaskRepo.findById(MessageTaskID).orElseThrow(
                () -> new ResourceNotFoundException("MessageTask is not exist with given id : " + MessageTaskID));

        return mapToDTO(messageTask);
    }


    @Override
    public void delete(Long MessageTaskID) {
        MessageTask messageTask = messageTaskRepo.findById(MessageTaskID).orElseThrow(
                () -> new ResourceNotFoundException("MessageTask is not exist with given id : " + MessageTaskID));

        messageTaskRepo.deleteById(MessageTaskID);
    }




    // convert entity into DTO
    private MessageTaskDto mapToDTO(MessageTask messageTask){
        MessageTaskDto messageTaskDto = mapper.map(messageTask, MessageTaskDto.class);
        return messageTaskDto;
    }

    // convert DTO to entity
    private MessageTask mapToEntity(MessageTaskDto messageTaskDto){
        MessageTask messageTask = mapper.map(messageTaskDto, MessageTask.class);
        return messageTask;
    }
}
