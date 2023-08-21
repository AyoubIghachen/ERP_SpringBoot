package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.ClientDto;
import com.allianceever.projectERP.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    // Build Get All Client REST API
    @GetMapping("/all")
    public ResponseEntity<List<ClientDto>> getAllClients(){
        List<ClientDto> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    // Build Get Client REST API
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Long clientID){
        ClientDto clientDto = clientService.getById(clientID);
        if (clientDto != null) {
            return ResponseEntity.ok(clientDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add Client REST API
    @PostMapping("/create")
    public ResponseEntity<ClientDto> createClient(@ModelAttribute ClientDto clientDto){
        ClientDto createdClient = clientService.create(clientDto);
        return new ResponseEntity<>(createdClient, CREATED);
    }

    // Build Multipart Update Client REST API
    @SneakyThrows
    @PostMapping("/updateClientMultipart")
    public ResponseEntity<ClientDto> updateClientMultipart(@ModelAttribute ClientDto clientDto, @RequestParam("imageFile") MultipartFile imageFile){
        String uploadDir = "./src/main/resources/static/assets/img/profiles/";
        Long ClientID = clientDto.getClientID();
        ClientDto existingClient = clientService.getById(ClientID);

        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = "imgC" + ClientID + "." + StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
            try {
                // Save the file in the specified upload directory
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Save the fileName in the database (you need to add a field for image name in your database)
                existingClient.setImageName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Perform a partial update of the existingClient using the clientDto data
        BeanUtils.copyProperties(clientDto, existingClient, getNullPropertyNames(clientDto));

        // Save the updated client data back to the database
        ClientDto updatedClient = clientService.update(ClientID,existingClient);
        return ResponseEntity.ok(updatedClient);
    }

    // Build Update Client REST API
    @PostMapping("/updateClient")
    public ResponseEntity<ClientDto> updateClient(@ModelAttribute ClientDto clientDto){
        Long ClientID = clientDto.getClientID();
        ClientDto existingClient = clientService.getById(ClientID);
        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingClient using the clientDto data
        BeanUtils.copyProperties(clientDto, existingClient, getNullPropertyNames(clientDto));

        // Save the updated client data back to the database
        ClientDto updatedClient = clientService.update(ClientID,existingClient);
        return ResponseEntity.ok(updatedClient);
    }

    // Build Delete Client REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long clientID){
        ClientDto clientDto = clientService.getById(clientID);
        if (clientDto != null) {
            clientService.delete(clientID);
            return ResponseEntity.ok("Client deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    // Helper method to get the names of null properties in the clientDto
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
