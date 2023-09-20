package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.dto.FileProjectDto;
import com.allianceever.projectERP.model.dto.ImageProjectDto;
import com.allianceever.projectERP.model.dto.LeaderProjectDto;
import com.allianceever.projectERP.service.EmployeeService;
import com.allianceever.projectERP.service.ImageProjectService;
import com.allianceever.projectERP.service.LeaderProjectService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/imageProject")
@AllArgsConstructor
public class ImageProjectController {
    private ImageProjectService imageProjectService;
    private LeaderProjectService leaderProjectService;
    private EmployeeService employeeService;

    // Build Get All ImageProject REST API
    @GetMapping("/all")
    public ResponseEntity<List<ImageProjectDto>> getAllImageProjects(){
        List<ImageProjectDto> imageProjects = imageProjectService.getAll();
        return ResponseEntity.ok(imageProjects);
    }

    // Build Get ImageProject REST API
    @GetMapping("/{id}")
    public ResponseEntity<ImageProjectDto> getImageProjectById(@PathVariable("id") Long imageProjectID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        ImageProjectDto imageProjectDto = imageProjectService.getById(imageProjectID);
        if (imageProjectDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                return ResponseEntity.ok(imageProjectDto);
            }else {
                String projectID = imageProjectDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    return ResponseEntity.ok(imageProjectDto);
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Build Multipart Add ImageProject REST API
    @SneakyThrows
    @PostMapping("/create")
    public ResponseEntity<ImageProjectDto> createImageProject(@ModelAttribute ImageProjectDto imageProjectDto, @RequestParam("imageFile") MultipartFile imageFile, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        // Generate date:
        DateFormat date_format_imageName = new SimpleDateFormat("ddMMyyHHmmss");
        Date date_imageName = new Date();
        String date = date_format_imageName.format(date_imageName);

        String uploadDir = "./src/main/resources/static/assets/img/projects/";
        String projectID = imageProjectDto.getProjectID();

        if(role.equals("ADMIN") || role.equals("Business_Development")){
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = "Project" + projectID + "_" + date + "." + StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
                try {
                    // Save the file in the specified upload directory
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // Save the fileName in the database (you need to add a field for image name in your database)
                    imageProjectDto.setImageName(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Set originalName
            String originalName = imageFile.getOriginalFilename();
            imageProjectDto.setOriginalName(originalName);

            ImageProjectDto createdImageProject = imageProjectService.create(imageProjectDto);
            return new ResponseEntity<>(createdImageProject, CREATED);
        }else {
            EmployeeDto employeeDto = employeeService.getByUsername(username);
            String employeeID = String.valueOf(employeeDto.getEmployeeID());

            LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
            if(leaderProjectDto != null){
                if (imageFile != null && !imageFile.isEmpty()) {
                    String fileName = "Project" + projectID + "_" + date + "." + StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
                    try {
                        // Save the file in the specified upload directory
                        Path filePath = Paths.get(uploadDir, fileName);
                        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        // Save the fileName in the database (you need to add a field for image name in your database)
                        imageProjectDto.setImageName(fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Set originalName
                String originalName = imageFile.getOriginalFilename();
                imageProjectDto.setOriginalName(originalName);

                ImageProjectDto createdImageProject = imageProjectService.create(imageProjectDto);
                return new ResponseEntity<>(createdImageProject, CREATED);
            }
            return ResponseEntity.notFound().build();
        }
    }


    // Build Delete ImageProject REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteImageProject(@PathVariable("id") Long imageProjectID, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt Token
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        ImageProjectDto imageProjectDto = imageProjectService.getById(imageProjectID);
        if (imageProjectDto != null) {
            if(role.equals("ADMIN") || role.equals("Business_Development")){
                imageProjectService.delete(imageProjectID);
                return ResponseEntity.ok("ImageProject deleted successfully!");
            }else {
                String projectID = imageProjectDto.getProjectID();
                EmployeeDto employeeDto = employeeService.getByUsername(username);
                String employeeID = String.valueOf(employeeDto.getEmployeeID());

                LeaderProjectDto leaderProjectDto = leaderProjectService.getByLeaderIDAndProjectID(employeeID, projectID);
                if(leaderProjectDto != null){
                    imageProjectService.delete(imageProjectID);
                    return ResponseEntity.ok("ImageProject deleted successfully!");
                }
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
