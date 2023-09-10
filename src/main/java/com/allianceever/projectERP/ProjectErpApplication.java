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
			roleRepository.save(new Role("USER"));
			roleRepository.save(new Role("SALES"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin1 = new ApplicationUser(1, "admin1", passwordEncode.encode("password1"), roles);
			ApplicationUser admin2 = new ApplicationUser(2, "admin2", passwordEncode.encode("password2"), roles);
			ApplicationUser admin3 = new ApplicationUser(3, "admin3", passwordEncode.encode("password3"), roles);

			userRepository.save(admin1);
			userRepository.save(admin2);
			userRepository.save(admin3);
		};
	}
}
