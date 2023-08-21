package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    // Build Get All Employee REST API
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    // Build Get Employee REST API
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeID){
        EmployeeDto employeeDto = employeeService.getById(employeeID);
        if (employeeDto != null) {
            return ResponseEntity.ok(employeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add Employee REST API
    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createEmployee(@ModelAttribute EmployeeDto employeeDto){
        EmployeeDto createdEmployee = employeeService.create(employeeDto);
        return new ResponseEntity<>(createdEmployee, CREATED);
    }

    // Build Multipart Update Employee REST API
    @SneakyThrows
    @PostMapping("/updateEmployeeMultipart")
    public ResponseEntity<EmployeeDto> updateEmployeeMultipart(@ModelAttribute EmployeeDto employeeDto, @RequestParam("imageFile") MultipartFile imageFile){
        String uploadDir = "./src/main/resources/static/assets/img/profiles/";
        Long EmployeeID = employeeDto.getEmployeeID();
        EmployeeDto existingEmployee = employeeService.getById(EmployeeID);

        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = "imgP" + EmployeeID + "." + StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
            try {
                // Save the file in the specified upload directory
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Save the fileName in the database (you need to add a field for image name in your database)
                existingEmployee.setImageName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Perform a partial update of the existingEmployee using the employeeDto data
        BeanUtils.copyProperties(employeeDto, existingEmployee, getNullPropertyNames(employeeDto));

        // Save the updated employee data back to the database
        EmployeeDto updatedEmployee = employeeService.update(EmployeeID,existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Build Update Employee REST API
    @PostMapping("/updateEmployee")
    public ResponseEntity<EmployeeDto> updateEmployee(@ModelAttribute EmployeeDto employeeDto){
        Long EmployeeID = employeeDto.getEmployeeID();
        EmployeeDto existingEmployee = employeeService.getById(EmployeeID);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingEmployee using the employeeDto data
        BeanUtils.copyProperties(employeeDto, existingEmployee, getNullPropertyNames(employeeDto));

        // Save the updated employee data back to the database
        EmployeeDto updatedEmployee = employeeService.update(EmployeeID,existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Build Delete Employee REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeID){
        EmployeeDto employeeDto = employeeService.getById(employeeID);
        if (employeeDto != null) {
            employeeService.delete(employeeID);
            return ResponseEntity.ok("Employee deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    // Helper method to get the names of null properties in the employeeDto
    public static String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }

    static String[] getStrings(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
