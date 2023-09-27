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
                auth.requestMatchers("/error-404.html").permitAll();
                // Authentication
                auth.requestMatchers("/login.html").permitAll();
                auth.requestMatchers("/auth/login").permitAll();
                auth.requestMatchers("/logout/").permitAll();

                auth.requestMatchers("/change-password.html").permitAll();// See again
                auth.requestMatchers("/auth/changePassword").authenticated();// See again
                // Dashboard
                auth.requestMatchers("/").permitAll();
                auth.requestMatchers("/index.html").permitAll();


                // All Employees Functions
                auth.requestMatchers("/employees.html").permitAll();
                auth.requestMatchers("/auth/register").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/auth/update").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/employee/all").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/employee/{id}").authenticated();
                auth.requestMatchers("/employee/create").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/employee/updateEmployeeMultipart").authenticated();
                auth.requestMatchers("/employee/updateEmployee").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/employee/delete/{id}").hasRole("ADMIN");
                auth.requestMatchers("/auth/delete/{id}").hasRole("ADMIN");
                auth.requestMatchers("/employee/searchEmployees/{search}").authenticated(); // See again(change)

                auth.requestMatchers("/profile.html/**").permitAll();

                auth.requestMatchers("/holidays.html").permitAll();
                auth.requestMatchers("/holiday/create").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/holiday/{holidayName}").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/holiday/updateHoliday").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/holiday/delete/{HolidayName}").hasAnyRole("ADMIN", "Human_Capital");

                auth.requestMatchers("/leave-type.html").permitAll();
                auth.requestMatchers("/leaveType/create").authenticated();
                auth.requestMatchers("/leaveType/{leaveTypeName}").authenticated();
                auth.requestMatchers("/leaveType/updateLeaveType").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/leaveType/delete/{LeaveTypeName}").authenticated();

                auth.requestMatchers("/leaves-employee.html").permitAll();
                auth.requestMatchers("/leaves/create").permitAll();
                auth.requestMatchers("/leaves/{Leaves}").permitAll();
                auth.requestMatchers("/leaves/updateLeaves").authenticated();
                auth.requestMatchers("/leaves/delete/{LeavesID}").authenticated();

                auth.requestMatchers("/leaves.html").permitAll();

                auth.requestMatchers("/departments.html").permitAll();
                auth.requestMatchers("/department/all").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/department/{id}").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/department/create").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/department/updateDepartment").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/department/delete/{id}").hasAnyRole("ADMIN", "Human_Capital");


                auth.requestMatchers("/designations.html").permitAll();
                auth.requestMatchers("/designation/all").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/designation/{id}").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/designation/create").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/designation/updateDesignation").hasAnyRole("ADMIN", "Human_Capital");
                auth.requestMatchers("/designation/delete/{id}").hasAnyRole("ADMIN", "Human_Capital");


                // Clients
                auth.requestMatchers("/clients.html").permitAll();
                auth.requestMatchers("/client-profile.html/**").permitAll();
                auth.requestMatchers("/client/all").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/client/{id}").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/client/create").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/client/updateClientMultipart").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/client/updateClient").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/client/delete/{id}").hasAnyRole("ADMIN", "Marketing", "Business_Development");


                // All Sales Functions
                auth.requestMatchers("/estimates.html").permitAll();
                auth.requestMatchers("/createEstimateInvoice/create").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/createEstimateInvoice/view/{id}").permitAll();
                auth.requestMatchers("/createEstimateInvoice/delete/{id}").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/createEstimateInvoice/edit/{id}").permitAll();
                auth.requestMatchers("/createEstimateInvoice/updateEstimatesInvoices").hasAnyRole("ADMIN", "Marketing", "Business_Development");

                auth.requestMatchers("/create-estimate.html").permitAll();
                auth.requestMatchers("/edit-estimate.html").permitAll();
                auth.requestMatchers("/estimate-view.html").permitAll();

                auth.requestMatchers("/invoices.html").permitAll();
                auth.requestMatchers("/create-invoice.html").permitAll();
                auth.requestMatchers("/edit-invoice.html").permitAll();
                auth.requestMatchers("/invoice-view.html").permitAll();

                auth.requestMatchers("/payments.html").permitAll();
                auth.requestMatchers("/payment/addPayment").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/payment/delete/{id}").hasAnyRole("ADMIN", "Marketing", "Business_Development");

                auth.requestMatchers("/expenses.html").permitAll();
                auth.requestMatchers("/expenses/create").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/expenses/delete/{id}").hasAnyRole("ADMIN", "Marketing", "Business_Development");
                auth.requestMatchers("/expenses/getExpense/{expenseId}").hasAnyRole("ADMIN", "Marketing", "Business_Development");


                // Projects
                auth.requestMatchers("/projects.html").permitAll();
                auth.requestMatchers("/project/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/project/{id}").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/project/create").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/project/updateProject").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/project/delete/{id}").hasAnyRole("ADMIN");

                auth.requestMatchers("/project-view.html/**").permitAll();
                auth.requestMatchers("/tasks.html/**").permitAll();
                //**
                auth.requestMatchers("/task/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/task/{id}").permitAll();
                auth.requestMatchers("/task/create").permitAll();
                auth.requestMatchers("/task/updateTask").permitAll();
                auth.requestMatchers("/task/delete/{id}").permitAll();

                auth.requestMatchers("/employeeProject/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/employeeProject/{id}").permitAll();
                auth.requestMatchers("/employeeProject/create").permitAll();
                auth.requestMatchers("/employeeProject/ByEmployeeIDAndProjectID").permitAll();
                auth.requestMatchers("/employeeProject/delete/{id}").permitAll();

                auth.requestMatchers("/employeeTask/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/employeeTask/{id}").permitAll();
                auth.requestMatchers("/employeeTask/create").permitAll();
                auth.requestMatchers("/employeeTask/ByEmployeeIDAndTaskID").permitAll();
                auth.requestMatchers("/employeeTask/delete/{id}").permitAll();
                auth.requestMatchers("/employeeTask/ByTaskID/{taskID}").permitAll();

                auth.requestMatchers("/leaderProject/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/leaderProject/{id}").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/leaderProject/create").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/leaderProject/ByLeaderIDAndProjectID").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/leaderProject/delete/{id}").hasAnyRole("ADMIN", "Business_Development");

                auth.requestMatchers("/messageTask/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/messageTask/{id}").permitAll();
                auth.requestMatchers("/messageTask/create").permitAll();
                auth.requestMatchers("/messageTask/ByTaskID/{taskID}").permitAll();
                auth.requestMatchers("/messageTask/delete/{id}").permitAll();

                auth.requestMatchers("/fileProject/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/fileProject/{id}").permitAll();
                auth.requestMatchers("/fileProject/create").permitAll();
                auth.requestMatchers("/fileProject/delete/{id}").permitAll();

                auth.requestMatchers("/imageProject/all").hasAnyRole("ADMIN", "Business_Development");
                auth.requestMatchers("/imageProject/{id}").permitAll();
                auth.requestMatchers("/imageProject/create").permitAll();
                auth.requestMatchers("/imageProject/delete/{id}").permitAll();
                //**


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
