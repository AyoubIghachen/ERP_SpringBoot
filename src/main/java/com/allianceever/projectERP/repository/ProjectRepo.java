package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
