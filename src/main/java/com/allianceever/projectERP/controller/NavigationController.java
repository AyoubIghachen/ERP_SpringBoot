package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.EmployeeDto;
import com.allianceever.projectERP.model.entity.Employee;
import com.allianceever.projectERP.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class NavigationController {
    private EmployeeService employeeService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/index.html")
    public String getIndex(){
        return "index";
    }

    //
    @GetMapping("/employees.html")
    public String ListEmployees(Model model){
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @RequestMapping("/profile.html")
    public String Profile(){
        return "profile";
    }

    @GetMapping("/profile.html/{id}")
    public String getProfile(@PathVariable("id") Long employeeID, Model model){
        model.addAttribute("employee", employeeService.getById(employeeID));
        return "profile";
    }
}
