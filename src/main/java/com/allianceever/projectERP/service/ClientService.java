package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto create(ClientDto clientDto);
    ClientDto update(Long clientID, ClientDto clientDto);
    List<ClientDto> getAll();
    ClientDto getById(Long clientID);
    void delete(Long clientID);
}
