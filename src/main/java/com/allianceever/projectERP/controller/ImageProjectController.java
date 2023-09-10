package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.ImageProjectDto;
import com.allianceever.projectERP.service.ImageProjectService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/imageProject")
@AllArgsConstructor
public class ImageProjectController {
    private ImageProjectService imageProjectService;

    // Build Get All ImageProject REST API
    @GetMapping("/all")
    public ResponseEntity<List<ImageProjectDto>> getAllImageProjects(){
        List<ImageProjectDto> imageProjects = imageProjectService.getAll();
        return ResponseEntity.ok(imageProjects);
    }

    // Build Get ImageProject REST API
    @GetMapping("/{id}")
    public ResponseEntity<ImageProjectDto> getImageProjectById(@PathVariable("id") Long imageProjectID){
        ImageProjectDto imageProjectDto = imageProjectService.getById(imageProjectID);
        if (imageProjectDto != null) {
            return ResponseEntity.ok(imageProjectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Build Multipart Add ImageProject REST API
    @SneakyThrows
    @PostMapping("/create")
    public ResponseEntity<ImageProjectDto> createImageProject(@ModelAttribute ImageProjectDto imageProjectDto, @RequestParam("imageFile") MultipartFile imageFile){
        // Generate date:
        DateFormat date_format_imageName = new SimpleDateFormat("ddMMyyHHmmss");
        Date date_imageName = new Date();
        String date = date_format_imageName.format(date_imageName);

        String uploadDir = "./src/main/resources/static/assets/img/projects/";
        String projectID = imageProjectDto.getProjectID();

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


    // Build Delete ImageProject REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteImageProject(@PathVariable("id") Long imageProjectID){
        ImageProjectDto imageProjectDto = imageProjectService.getById(imageProjectID);
        if (imageProjectDto != null) {
            imageProjectService.delete(imageProjectID);
            return ResponseEntity.ok("ImageProject deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
