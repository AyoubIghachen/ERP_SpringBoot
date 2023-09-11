package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.LeavesDto;
import com.allianceever.projectERP.service.LeavesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.allianceever.projectERP.controller.EmployeeController.getStrings;

@RestController
@RequestMapping("/leaves")
@ComponentScan(basePackages = "com.allianceever.projectERP")
public class LeavesController {


    private LeavesService leavesService;

    @Autowired
    public LeavesController(LeavesService leavesService) {
        this.leavesService = leavesService;
    }

    @PostMapping("/create")
    public ModelAndView createLeaves(@ModelAttribute() LeavesDto leavesDto ,@RequestParam("activePage") String activePage){
        LeavesDto createdHoliday = leavesService.create(leavesDto);
        //return new ResponseEntity<>(createdHoliday, CREATED);
        String redirectPage="redirect:/leaves-employee.html";
        if ("leaves".equals(activePage)) {
            redirectPage = "redirect:/leaves.html";
        } else if ("leaves-employee".equals(activePage)) {
            redirectPage = "redirect:/leaves-employee.html";
        }
        return new ModelAndView(redirectPage);
    }

    @GetMapping("/{Leaves}")
    public ResponseEntity<LeavesDto> getLeavesByLeavesID(@PathVariable("Leaves") Integer LeavesID){
        LeavesDto leavesDto = leavesService.getByLeavesID(LeavesID);
        if (leavesDto != null) {
            return ResponseEntity.ok(leavesDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/updateLeaves")
    public ResponseEntity<LeavesDto> updateLeaves(@ModelAttribute LeavesDto leavesDto){
        Integer LeavesID = leavesDto.getLeavesID();
        LeavesDto existingLeaves = leavesService.getByLeavesID(LeavesID);
        if (existingLeaves == null) {
            return ResponseEntity.notFound().build();
        }
        // Perform a partial update of the existingEmployee using the employeeDto data
        BeanUtils.copyProperties(leavesDto, existingLeaves, getNullPropertyNames(leavesDto));

        // Save the updated employee data back to the database
        LeavesDto updatedLeaves = leavesService.update(LeavesID,existingLeaves);
        return ResponseEntity.ok(updatedLeaves);
    }


    // Build Delete Employee REST API
    @DeleteMapping("/delete/{LeavesID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("LeavesID")  Integer LeavesID){
        LeavesDto leavesDto = leavesService.getByLeavesID(LeavesID);
        // if (employeeDto != null) {
        //   employeeService.delete(employeeID);
        //   return ResponseEntity.ok("Employee deleted successfully!");
        //  } else {
        //    return ResponseEntity.notFound().build();
        //  }

        leavesService.delete(LeavesID);
        return ResponseEntity.ok("Holiday deleted successfully!");
    }


    public static String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }



}
