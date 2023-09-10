package com.allianceever.projectERP.repository;


import com.allianceever.projectERP.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findByEmail(String Email);

    // Use a custom JPQL query for a case-insensitive substring search on first name
    @Query("SELECT e FROM Employee e WHERE LOWER(e.first_Name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Employee> findByFirst_NameLikeIgnoreCase(String searchTerm);

    //
    Employee findByUserName(String username);
}
