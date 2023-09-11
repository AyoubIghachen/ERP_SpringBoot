package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.HolidayDto;
import com.allianceever.projectERP.service.HolidayService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.allianceever.projectERP.controller.EmployeeController.getStrings;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/holiday")
@ComponentScan(basePackages = "com.allianceever.projectERP")
@AllArgsConstructor // Add this annotation to generate a constructor with all required dependencies
public class HolidayController {

    private HolidayService holidayService;


    @PostMapping("/create")
    public ResponseEntity<HolidayDto> createHoliday(@ModelAttribute() HolidayDto holidayDto){
        HolidayDto createdHoliday = holidayService.create(holidayDto);
        return new ResponseEntity<>(createdHoliday, CREATED);
    }

    @GetMapping("/{holidayName}")
    public ResponseEntity<HolidayDto> getHolidayByHolidayName(@PathVariable("holidayName") String holidayName){
        HolidayDto holidayDto = holidayService.getByHolidayName(holidayName);
        if (holidayDto != null) {
            return ResponseEntity.ok(holidayDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/updateHoliday")
    public ResponseEntity<HolidayDto> updateHoliday(@ModelAttribute HolidayDto holidayDto){
        String HolidayName = holidayDto.getHolidayName();
        HolidayDto existingHoliday = holidayService.getByHolidayName(HolidayName);
       if (existingHoliday == null) {
            return ResponseEntity.notFound().build();
       }
        // Perform a partial update of the existingEmployee using the employeeDto data
        BeanUtils.copyProperties(holidayDto, existingHoliday, getNullPropertyNames(holidayDto));

        // Save the updated employee data back to the database
        HolidayDto updatedHoliday = holidayService.update(HolidayName,existingHoliday);
        return ResponseEntity.ok(updatedHoliday);
    }


    // Build Delete Employee REST API
    @PostMapping("/delete/{HolidayName}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("HolidayName")  String holidayName){
        HolidayDto holidayDto = holidayService.getByHolidayName(holidayName);
       // if (employeeDto != null) {
         //   employeeService.delete(employeeID);
         //   return ResponseEntity.ok("Employee deleted successfully!");
      //  } else {
        //    return ResponseEntity.notFound().build();
      //  }

        holidayService.delete(holidayName);
        return ResponseEntity.ok("Holiday deleted successfully!");
    }


    public static String[] getNullPropertyNames(Object source) {
        return getStrings(source);
    }
}




