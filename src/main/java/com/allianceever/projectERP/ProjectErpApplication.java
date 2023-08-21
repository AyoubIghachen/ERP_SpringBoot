package com.allianceever.projectERP;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
@EnableWebSecurity
@SpringBootApplication
public class ProjectErpApplication  {



	public static void main(String[] args) {
		SpringApplication.run(ProjectErpApplication.class, args);
	}
}
