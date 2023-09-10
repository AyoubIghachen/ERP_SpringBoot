package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.FileProjectDto;
import com.allianceever.projectERP.service.FileProjectService;
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


@RestController
@RequestMapping("/fileProject")
@AllArgsConstructor
@CrossOrigin("*")
public class FileProjectController {
    private FileProjectService fileProjectService;

    // Build Get All FileProject REST API
    @GetMapping("/all")
    public ResponseEntity<List<FileProjectDto>> getAllFileProjects(){
        List<FileProjectDto> fileProjects = fileProjectService.getAll();
        return ResponseEntity.ok(fileProjects);
    }

    // Build Get FileProject REST API
    @GetMapping("/{id}")
    public ResponseEntity<FileProjectDto> getFileProjectById(@PathVariable("id") Long fileProjectID){
        FileProjectDto fileProjectDto = fileProjectService.getById(fileProjectID);
        if (fileProjectDto != null) {
            return ResponseEntity.ok(fileProjectDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Build Multipart Add FileProject REST API
    @SneakyThrows
    @PostMapping("/create")
    public ResponseEntity<FileProjectDto> createFileProject(@ModelAttribute FileProjectDto fileProjectDto, @RequestParam("fileFile") MultipartFile fileFile){
        // Generate date:
        DateFormat date_format_fileName = new SimpleDateFormat("ddMMyyHHmmss");
        Date date_fileName = new Date();
        String date = date_format_fileName.format(date_fileName);

        String uploadDir = "./src/main/resources/static/assets/img/projects/";
        String projectID = fileProjectDto.getProjectID();

        if (fileFile != null && !fileFile.isEmpty()) {
            String fileName = "Project" + projectID + "_" + date + "." + StringUtils.getFilenameExtension(fileFile.getOriginalFilename());
            try {
                // Save the file in the specified upload directory
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(fileFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Save the fileName in the database (you need to add a field for file name in your database)
                fileProjectDto.setFileName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Set originalName
        String originalName = fileFile.getOriginalFilename();
        fileProjectDto.setOriginalName(originalName);

        // Generate date for file:
        DateFormat date_format1 = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat date_format2 = new SimpleDateFormat("HH:mm");
        Date dateFile = new Date();
        String dateCreation = date_format1.format(dateFile) + " at " + date_format2.format(dateFile);

        // Set dateCreation
        fileProjectDto.setDateCreation(dateCreation);

        FileProjectDto createdFileProject = fileProjectService.create(fileProjectDto);
        return new ResponseEntity<>(createdFileProject, CREATED);
    }


    // Build Delete FileProject REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteFileProject(@PathVariable("id") Long fileProjectID){
        FileProjectDto fileProjectDto = fileProjectService.getById(fileProjectID);
        if (fileProjectDto != null) {
            fileProjectService.delete(fileProjectID);
            return ResponseEntity.ok("FileProject deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
