package com.allianceever.projectERP.service;

import com.allianceever.projectERP.model.dto.LeaderProjectDto;

import java.util.List;

public interface LeaderProjectService {
    LeaderProjectDto create(LeaderProjectDto leaderProjectDto);
    List<LeaderProjectDto> getAll();
    List<LeaderProjectDto> findAll(String projectID);
    List<LeaderProjectDto> findAllByLeaderID(String leaderID);
    LeaderProjectDto getById(Long leaderProjectID);
    LeaderProjectDto getByLeaderIDAndProjectID(String leaderID, String projectID);
    void delete(Long leaderProjectID);
}
