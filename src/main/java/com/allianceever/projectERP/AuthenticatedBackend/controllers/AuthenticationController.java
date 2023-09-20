package com.allianceever.projectERP.AuthenticatedBackend.controllers;

import com.allianceever.projectERP.AuthenticatedBackend.models.ChangePasswordDTO;
import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.service.EmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.allianceever.projectERP.AuthenticatedBackend.models.ApplicationUser;
import com.allianceever.projectERP.AuthenticatedBackend.models.LoginResponseDTO;
import com.allianceever.projectERP.AuthenticatedBackend.models.RegistrationDTO;
import com.allianceever.projectERP.AuthenticatedBackend.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<LoginResponseDTO> loginUser(@ModelAttribute RegistrationDTO body, HttpServletResponse response){
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getUsername(), body.getPassword());
        if (loginResponseDTO.getUser() != null) {
            String jwtToken = loginResponseDTO.getJwt();
            // Set the JWT token in an HTTP-only cookie
            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setPath("/"); // Set the path to "/" to make the cookie accessible everywhere on your website
            response.addCookie(cookie);

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

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@ModelAttribute ChangePasswordDTO body, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username from the jwt
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");
        // verify username and password
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(username, body.getOldPassword());
        if (loginResponseDTO.getUser() != null && !body.getNewPassword().equals("")) {
            ApplicationUser applicationUser = authenticationService.updateUser(username, body.getNewPassword(), role);
            if(applicationUser != null){
                return ResponseEntity.ok("Password changed successfully!");
            }else{
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
