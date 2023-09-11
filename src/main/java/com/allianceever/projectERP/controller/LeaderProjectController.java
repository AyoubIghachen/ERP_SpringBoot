package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.LeaderProjectDto;
import com.allianceever.projectERP.service.LeaderProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/leaderProject")
@AllArgsConstructor
public class LeaderProjectController {
    private LeaderProjectService leaderProjectService;

    // Build Get All LeaderProject REST API
    @GetMapping("/all")
    public ResponseEntity<List<LeaderProjectDto>> getAllLeaderProjects(){
        List<LeaderProjectDto> leaderProjects = leaderProjectService.getAll();
        return ResponseEntity.ok(leaderProjects);
    }

    // Build Get LeaderProject REST API
    @GetMapping("/{id}")
    public ResponseEntity<LeaderProjectDto> getLeaderProjectById(@PathVariable("id") Long leaderProjectID){
        LeaderProjectDto leaderProjectDto = leaderProjectService.getById(leaderProjectID);
        if (leaderProjectDto != null) {
            return ResponseEntity.ok(leaderProjectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Get LeaderProject By leaderID And projectID REST API
    @GetMapping("/ByLeaderIDAndProjectID")
    public ResponseEntity<LeaderProjectDto> getLeaderProjectByLeaderIDAndProjectID(@RequestParam("leaderID") String leaderID, @RequestParam("projectID") String projectID){
        LeaderProjectDto existeLeaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(leaderID, projectID);
        if (existeLeaderProjectDto != null) {
            return ResponseEntity.ok(existeLeaderProjectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add LeaderProject REST API
    @PostMapping("/create")
    public ResponseEntity<LeaderProjectDto> createLeaderProject(@ModelAttribute LeaderProjectDto leaderProjectDto){
        LeaderProjectDto createdLeaderProject = leaderProjectService.create(leaderProjectDto);
        return new ResponseEntity<>(createdLeaderProject, CREATED);
    }


    // Build Delete LeaderProject REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaderProject(@PathVariable("id") Long leaderProjectID){
        LeaderProjectDto leaderProjectDto = leaderProjectService.getById(leaderProjectID);
        if (leaderProjectDto != null) {
            leaderProjectService.delete(leaderProjectID);
            return ResponseEntity.ok("LeaderProject deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
