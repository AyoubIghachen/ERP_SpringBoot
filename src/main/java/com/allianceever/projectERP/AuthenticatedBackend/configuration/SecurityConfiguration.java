package com.allianceever.projectERP.AuthenticatedBackend.configuration;

import com.allianceever.projectERP.AuthenticatedBackend.utils.TokenValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.allianceever.projectERP.AuthenticatedBackend.utils.RSAKeyProperties;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfiguration {

    private final RSAKeyProperties keys;
    private final TokenValidationFilter tokenValidationFilter;

    @Autowired
    public SecurityConfiguration(RSAKeyProperties keys, TokenValidationFilter tokenValidationFilter) {
        this.keys = keys;
        this.tokenValidationFilter = tokenValidationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService){
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(tokenValidationFilter, UsernamePasswordAuthenticationFilter.class) // Add the filter
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/assets/**").permitAll();
                auth.requestMatchers("/login.html").permitAll();
                auth.requestMatchers("/auth/login").permitAll();
                auth.requestMatchers("/logout/").permitAll();

                auth.requestMatchers("/auth/register").hasRole("ADMIN");
                auth.requestMatchers("/auth/update").hasRole("ADMIN");
                auth.requestMatchers("/auth/changePassword").authenticated();

                auth.requestMatchers("/").permitAll();
                auth.requestMatchers("/index.html").permitAll();
                auth.requestMatchers("/employees.html").permitAll();
                auth.requestMatchers("/settings.html").permitAll();
                auth.requestMatchers("/change-password.html").permitAll();

                auth.requestMatchers("/employee/**").hasRole("ADMIN");
                // Permit access to any URL for users with role "ADMIN"
                auth.requestMatchers("/**").hasRole("ADMIN");

                auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER");
                auth.requestMatchers("/sales/**").hasAnyRole("ADMIN", "SALES");
                auth.anyRequest().authenticated();
            });

        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
                
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
    
}
