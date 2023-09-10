package com.allianceever.projectERP.AuthenticatedBackend.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allianceever.projectERP.AuthenticatedBackend.models.ApplicationUser;
import com.allianceever.projectERP.AuthenticatedBackend.models.LoginResponseDTO;
import com.allianceever.projectERP.AuthenticatedBackend.models.Role;
import com.allianceever.projectERP.AuthenticatedBackend.repository.RoleRepository;
import com.allianceever.projectERP.AuthenticatedBackend.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ApplicationUser registerUser(String username, String password, String role){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(role).get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

    public ApplicationUser updateUser(String username, String password, String role){

        ApplicationUser applicationUser = userRepository.findByUsername(username).get();

        if(!password.equals("")){
            String encodedPassword = passwordEncoder.encode(password);
            applicationUser.setPassword(encodedPassword);
        }

        Role userRole = roleRepository.findByAuthority(role).get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        applicationUser.setAuthorities(authorities);
        return userRepository.save(applicationUser);
    }

    public void delete(String username){
        ApplicationUser applicationUser = userRepository.findByUsername(username).get();
        Integer userId = applicationUser.getUserId();
        userRepository.deleteById(userId);
    }

}
