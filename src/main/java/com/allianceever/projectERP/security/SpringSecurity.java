package com.allianceever.projectERP.security;


import com.allianceever.projectERP.config.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {


////
    private CustomUserDetailsService customUserDetailsServices;

    @Autowired
    public SpringSecurity(CustomUserDetailsService customUserDetailsServices) {
        this.customUserDetailsServices = customUserDetailsServices;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // configure SecurityFilterChain
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.anyRequest().permitAll())
                //.requestMatchers("/register/**").permitAll()
                //.antMatchers("/users").hasRole("ADMIN") // If you have specific role-based access for "/users"
                //.antMatchers("/users").hasRole("USER")  // If you have specific role-based access for "/users"
                //.anyRequest().authenticated()

               // .requestMatchers("/login").permitAll()
                //.requestMatchers("/users").hasRole("ADMIN")
                //.requestMatchers("/users").hasRole("USER")
                //.and()

                .formLogin(
                        form -> form
                                //.usernameParameter("username")
                                //.passwordParameter("password")

                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .usernameParameter("username").passwordParameter("password")

                                .defaultSuccessUrl("/",true).permitAll()
                                .failureHandler(new AuthenticationFailureHandler() {
                                    @Override
                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                        System.out.println("Error :"+exception.getMessage());
                                        UrlPathHelper helper=new UrlPathHelper();
                                        response.sendRedirect("/login?error");
                                    }
                                })
                )

                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()

                );
        return http.build();
    }


}
