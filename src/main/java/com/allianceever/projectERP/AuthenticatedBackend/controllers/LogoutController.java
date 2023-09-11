package com.allianceever.projectERP.AuthenticatedBackend.controllers;

import com.allianceever.projectERP.AuthenticatedBackend.models.BlacklistedToken;
import com.allianceever.projectERP.AuthenticatedBackend.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private final TokenBlacklistRepository blacklistRepository;

    @Autowired
    public LogoutController(TokenBlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    @PostMapping("/")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Extract the token from the "Authorization" header (assuming it's in the format "Bearer {token}")
        String jwtToken = token.substring(7); // Remove "Bearer " prefix

        // Create a BlacklistedToken entity and save it to the repository
        BlacklistedToken blacklistedToken = new BlacklistedToken(jwtToken);
        blacklistRepository.save(blacklistedToken);

        // Respond with success
        return ResponseEntity.ok("Logout successful");
    }
}
