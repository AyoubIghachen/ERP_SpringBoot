package com.allianceever.projectERP.AuthenticatedBackend.controllers;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.allianceever.projectERP.AuthenticatedBackend.models.ApplicationUser;
import com.allianceever.projectERP.AuthenticatedBackend.models.LoginResponseDTO;
import com.allianceever.projectERP.AuthenticatedBackend.models.RegistrationDTO;
import com.allianceever.projectERP.AuthenticatedBackend.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@ModelAttribute RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getRole());
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@ModelAttribute RegistrationDTO body){
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getUsername(), body.getPassword());
        if (loginResponseDTO.getUser() != null) {
            return ResponseEntity.ok(loginResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update")
    public ApplicationUser updateUser(@ModelAttribute RegistrationDTO body){
        return authenticationService.updateUser(body.getUsername(), body.getPassword(), body.getRole());
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long employeeID){
        EmployeeDto employeeDto = employeeService.getById(employeeID);
        if (employeeDto != null) {
            String username = employeeDto.getUserName();
            authenticationService.delete(username);
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
