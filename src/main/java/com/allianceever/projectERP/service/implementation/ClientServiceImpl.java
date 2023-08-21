package com.allianceever.projectERP.service.implementation;

import com.allianceever.projectERP.exception.ResourceNotFoundException;
import com.allianceever.projectERP.model.dto.ClientDto;
import com.allianceever.projectERP.model.entity.Client;
import com.allianceever.projectERP.repository.ClientRepo;
import com.allianceever.projectERP.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepo clientRepo;
    private ModelMapper mapper;
    @Override
    public ClientDto create(ClientDto clientDto) {
        // convert DTO to entity
        Client client = mapToEntity(clientDto);
        Client newClient = clientRepo.save(client);

        // convert entity to DTO
        return mapToDTO(newClient);
    }

    @Override
    public ClientDto update(Long ClientID, ClientDto clientDto) {
        clientRepo.findById(ClientID).orElseThrow(
                () -> new ResourceNotFoundException("Client is not exist with given id : " + ClientID)
        );
        // convert DTO to entity
        Client client = mapToEntity(clientDto);
        Client updatedClient = clientRepo.save(client);

        // convert entity to DTO
        return mapToDTO(updatedClient);
    }

    @Override
    public List<ClientDto> getAll() {
        List<Client> clients = clientRepo.findAll();
        return clients.stream().map((client) -> mapToDTO(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getById(Long ClientID) {
        Client client = clientRepo.findById(ClientID).orElseThrow(
                () -> new ResourceNotFoundException("Client is not exist with given id : " + ClientID));

        return mapToDTO(client);
    }

    @Override
    public void delete(Long ClientID) {
        Client client = clientRepo.findById(ClientID).orElseThrow(
                () -> new ResourceNotFoundException("Client is not exist with given id : " + ClientID));

        clientRepo.deleteById(ClientID);
    }




    // convert entity into DTO
    private ClientDto mapToDTO(Client client){
        ClientDto clientDto = mapper.map(client, ClientDto.class);
        return clientDto;
    }

    // convert DTO to entity
    private Client mapToEntity(ClientDto clientDto){
        Client client = mapper.map(clientDto, Client.class);
        return client;
    }
}
