package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.DepartmentDto;
import com.allianceever.projectERP.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/department")
@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    // Build Get All Department REST API
    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> departments = departmentService.getAll();
        return ResponseEntity.ok(departments);
    }

    // Build Get Department REST API
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long departmentID){
        DepartmentDto departmentDto = departmentService.getById(departmentID);
        if (departmentDto != null) {
            return ResponseEntity.ok(departmentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add Department REST API
    @PostMapping("/create")
    public ResponseEntity<DepartmentDto> createDepartment(@ModelAttribute DepartmentDto departmentDto){
        DepartmentDto createdDepartment = departmentService.create(departmentDto);
        return new ResponseEntity<>(createdDepartment, CREATED);
    }

    // Build Update Department REST API
    @PostMapping("/updateDepartment")
    public ResponseEntity<DepartmentDto> updateDepartment(@ModelAttribute DepartmentDto departmentDto){
        Long DepartmentID = departmentDto.getDepartmentID();
        DepartmentDto existingDepartment = departmentService.getById(DepartmentID);
        if (existingDepartment == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingDepartment using the departmentDto data
        BeanUtils.copyProperties(departmentDto, existingDepartment, getNullPropertyNames(departmentDto));

        // Save the updated department data back to the database
        DepartmentDto updatedDepartment = departmentService.update(DepartmentID,existingDepartment);
        return ResponseEntity.ok(updatedDepartment);
    }

    // Build Delete Department REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentID){
        DepartmentDto departmentDto = departmentService.getById(departmentID);
        if (departmentDto != null) {
            departmentService.delete(departmentID);
            return ResponseEntity.ok("Department deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    // Helper method to get the names of null properties in the departmentDto
    public static String[] getNullPropertyNames(Object source) {
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
