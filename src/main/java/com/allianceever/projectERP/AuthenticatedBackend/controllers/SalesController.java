package com.allianceever.projectERP.AuthenticatedBackend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@CrossOrigin("*")
public class SalesController {

    @GetMapping("/")
    public String helloSalesController(){
        return "Sales access level";
    }

}