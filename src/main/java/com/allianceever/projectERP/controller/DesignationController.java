package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.DesignationDto;
import com.allianceever.projectERP.service.DesignationService;
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
@RequestMapping("/designation")
@AllArgsConstructor
public class DesignationController {
    private DesignationService designationService;

    // Build Get All Designation REST API
    @GetMapping("/all")
    public ResponseEntity<List<DesignationDto>> getAllDesignations(){
        List<DesignationDto> designations = designationService.getAll();
        return ResponseEntity.ok(designations);
    }

    // Build Get Designation REST API
    @GetMapping("/{id}")
    public ResponseEntity<DesignationDto> getDesignationById(@PathVariable("id") Long designationID){
        DesignationDto designationDto = designationService.getById(designationID);
        if (designationDto != null) {
            return ResponseEntity.ok(designationDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Build Add Designation REST API
    @PostMapping("/create")
    public ResponseEntity<DesignationDto> createDesignation(@ModelAttribute DesignationDto designationDto){
        DesignationDto createdDesignation = designationService.create(designationDto);
        return new ResponseEntity<>(createdDesignation, CREATED);
    }

    // Build Update Designation REST API
    @PostMapping("/updateDesignation")
    public ResponseEntity<DesignationDto> updateDesignation(@ModelAttribute DesignationDto designationDto){
        Long DesignationID = designationDto.getDesignationID();
        DesignationDto existingDesignation = designationService.getById(DesignationID);
        if (existingDesignation == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingDesignation using the designationDto data
        BeanUtils.copyProperties(designationDto, existingDesignation, getNullPropertyNames(designationDto));

        // Save the updated designation data back to the database
        DesignationDto updatedDesignation = designationService.update(DesignationID,existingDesignation);
        return ResponseEntity.ok(updatedDesignation);
    }

    // Build Delete Designation REST API
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteDesignation(@PathVariable("id") Long designationID){
        DesignationDto designationDto = designationService.getById(designationID);
        if (designationDto != null) {
            designationService.delete(designationID);
            return ResponseEntity.ok("Designation deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    // Helper method to get the names of null properties in the designationDto
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
