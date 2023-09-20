package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.LeaderProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaderProjectRepo extends JpaRepository<LeaderProject, Long> {
    List<LeaderProject> findByProjectID(String projectID);
    List<LeaderProject> findByLeaderID(String leaderID);
    LeaderProject findByLeaderIDAndProjectID(String leaderID, String projectID);
}
