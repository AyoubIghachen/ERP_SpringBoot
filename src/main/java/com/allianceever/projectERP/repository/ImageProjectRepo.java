package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.ImageProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageProjectRepo extends JpaRepository<ImageProject, Long> {
    List<ImageProject> findByProjectID(String projectID);
}
