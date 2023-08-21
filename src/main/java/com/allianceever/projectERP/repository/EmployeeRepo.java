package com.allianceever.projectERP.repository;


import com.allianceever.projectERP.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findByEmail(String Email);
}
