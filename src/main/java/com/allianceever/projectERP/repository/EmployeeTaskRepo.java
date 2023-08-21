package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.EmployeeTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTaskRepo extends JpaRepository<EmployeeTask, Long> {
    List<EmployeeTask> findByTaskID(String taskID);
    EmployeeTask findByEmployeeIDAndTaskID(String employeeID, String taskID);
}
