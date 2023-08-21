package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByProjectID(String projectID);
}
