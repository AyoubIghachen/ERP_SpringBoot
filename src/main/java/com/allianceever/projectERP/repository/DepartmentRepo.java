package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
