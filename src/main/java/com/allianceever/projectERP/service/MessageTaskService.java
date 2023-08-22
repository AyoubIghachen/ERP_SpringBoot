package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.MessageTaskDto;

import java.util.List;

public interface MessageTaskService {
    MessageTaskDto create(MessageTaskDto messageTaskDto);
    List<MessageTaskDto> getAll();
    List<MessageTaskDto> findAll(String taskID);
    MessageTaskDto getById(Long messageTaskID);
    void delete(Long messageTaskID);
}
