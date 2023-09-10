package com.allianceever.projectERP.AuthenticatedBackend.services;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    // Define the token expiration time in seconds (e.g., 30 minutes)
    private static final long TOKEN_EXPIRATION_SECONDS = 30 * 60; // 30 minutes

    public String generateJwt(Authentication auth){

        Instant now = Instant.now();

        // Calculate the expiration time based on the current time
        Instant expirationTime = now.plusSeconds(TOKEN_EXPIRATION_SECONDS);

        String scope = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(expirationTime)
            .subject(auth.getName())
            .claim("roles", scope)
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
