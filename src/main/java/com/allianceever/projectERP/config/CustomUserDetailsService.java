package com.allianceever.projectERP.config;

import com.allianceever.projectERP.model.entity.Employee;
import com.allianceever.projectERP.repository.EmployeeRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   // private AdminRepo adminRepo;
    private EmployeeRepo employeeRepo;

    //public CustomUserDetailsService(AdminRepo adminRepo, EmployeeRepo employeeRepo) {
      //  this.adminRepo = adminRepo;
        //this.employeeRepo = employeeRepo;
    //}

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {



        Employee employee = employeeRepo.findByEmail(usernameOrEmail);
        if (employee != null) {
            // Assuming "ROLE_EMPLOYEE" is the authority/role for employee users
            return new CustomUserDetails(
                    employee.getEmail(),
                    employee.getPassword(),
                    authorities ());
        }

        throw new UsernameNotFoundException("Invalid email or password");
    }

    // Method to return authorities for the user
    public Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }


}
