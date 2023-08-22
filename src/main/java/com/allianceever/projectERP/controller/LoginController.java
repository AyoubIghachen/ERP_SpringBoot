package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private EmployeeService employeeService;

    // Build Get MessageTask REST API
    @PostMapping("/")
    public ResponseEntity<EmployeeDto> verifyRegistration(@ModelAttribute EmployeeDto employeeDto, HttpSession session){
        // Encode the password
        String encodedPassword = passwordEncoder.encode(employeeDto.getPassword());
        String email = employeeDto.getEmail();

        String employeeID = employeeService.isRegistred(email,employeeDto.getPassword());

        if (employeeID != "-1") {
            session.setAttribute("employeeID", employeeID);
            EmployeeDto employeeDto1 = employeeService.getById(Long.parseLong(employeeID));
            return ResponseEntity.ok(employeeDto1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
