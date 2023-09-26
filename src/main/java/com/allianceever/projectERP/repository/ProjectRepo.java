package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.company_Name = :companyName")
    List<Project> findByCompany_Name(@Param("companyName") String companyName);
}
