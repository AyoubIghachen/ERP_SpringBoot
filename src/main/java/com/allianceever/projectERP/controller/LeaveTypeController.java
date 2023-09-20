package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.LeaveTypeDto;
import com.allianceever.projectERP.service.LeaveTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static com.allianceever.projectERP.controller.EmployeeController.getStrings;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/leaveType")
@ComponentScan(basePackages = "com.allianceever.projectERP")
public class LeaveTypeController {

    private LeaveTypeService leaveTypeService;

    @Autowired
    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<LeaveTypeDto> createLeaveType(@ModelAttribute() LeaveTypeDto leaveTypeDto, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username from the jwt
        String username = jwt.getClaimAsString("sub");

        String leaveName = leaveTypeDto.getLeaveName()+"("+username+")";

        leaveTypeDto.setUsername(username);
        leaveTypeDto.setLeaveName(leaveName);
        LeaveTypeDto createdLeaveType = leaveTypeService.create(leaveTypeDto);
        return new ResponseEntity<>(createdLeaveType, CREATED);
    }

    @GetMapping("/{leaveTypeName}")
    public ResponseEntity<LeaveTypeDto> getHolidayByHolidayName(@PathVariable("leaveTypeName") String leaveTypeName, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        LeaveTypeDto leaveTypeDto = leaveTypeService.getByLeaveName(leaveTypeName);
        if (leaveTypeDto != null) {
            if(leaveTypeDto.getUsername().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
                return ResponseEntity.ok(leaveTypeDto);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/updateLeaveType")
    public ResponseEntity<LeaveTypeDto> updateLeaveType(@ModelAttribute LeaveTypeDto leaveTypeDto){
        String LeaveTypeName = leaveTypeDto.getLeaveName();
        LeaveTypeDto existingLeaveType = leaveTypeService.getByLeaveName(LeaveTypeName);
        if (existingLeaveType == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingEmployee using the employeeDto data
        BeanUtils.copyProperties(leaveTypeDto, existingLeaveType, getNullPropertyNames(leaveTypeDto));

        // Save the updated employee data back to the database
        LeaveTypeDto updatedLeaveType = leaveTypeService.update(LeaveTypeName,existingLeaveType);
        return ResponseEntity.ok(updatedLeaveType);
    }


    // Build Delete Employee REST API
    @Transactional
    @PostMapping("/delete/{LeaveTypeName}")
    public ResponseEntity<String> deleteLeaveType(@PathVariable("LeaveTypeName")  String leaveName, @AuthenticationPrincipal Jwt jwt){
        // Retrieve username and role from the jwt
        String username = jwt.getClaimAsString("sub");
        String role = jwt.getClaimAsString("roles");

        LeaveTypeDto leaveTypeDto = leaveTypeService.getByLeaveName(leaveName);

        if (leaveTypeDto != null) {
            if(leaveTypeDto.getUsername().equals(username) || role.equals("ADMIN") || role.equals("Human_Capital")){
                leaveTypeService.delete(leaveName);
                return ResponseEntity.ok("LeaveType deleted successfully!");
            }
        }
        return ResponseEntity.notFound().build();
    }


    public static String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }
}
