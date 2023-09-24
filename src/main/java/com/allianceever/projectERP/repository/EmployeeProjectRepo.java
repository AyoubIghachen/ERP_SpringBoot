package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeProjectRepo extends JpaRepository<EmployeeProject, Long> {
    List<EmployeeProject> findByProjectID(String projectID);
    List<EmployeeProject> findByEmployeeID(String employeeID);
    EmployeeProject findByEmployeeIDAndProjectID(String employeeID, String projectID);
}
