package com.allianceever.projectERP;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import com.allianceever.projectERP.AuthenticatedBackend.models.ApplicationUser;
import com.allianceever.projectERP.AuthenticatedBackend.models.Role;
import com.allianceever.projectERP.AuthenticatedBackend.repository.RoleRepository;
import com.allianceever.projectERP.AuthenticatedBackend.repository.UserRepository;

@SpringBootApplication
public class ProjectErpApplication  {
	public static void main(String[] args) {
		SpringApplication.run(ProjectErpApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("Employee"));
			roleRepository.save(new Role("Marketing"));
			roleRepository.save(new Role("IT"));
			roleRepository.save(new Role("Human_Capital"));
			roleRepository.save(new Role("Business_Development"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			userRepository.save(new ApplicationUser(1, "admin1", passwordEncode.encode("password1"), roles));
			userRepository.save(new ApplicationUser(2, "admin2", passwordEncode.encode("password2"), roles));
			userRepository.save(new ApplicationUser(3, "admin3", passwordEncode.encode("password3"), roles));
		};
	}
}
