package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.FileProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileProjectRepo extends JpaRepository<FileProject, Long> {
    List<FileProject> findByProjectID(String projectID);
}
