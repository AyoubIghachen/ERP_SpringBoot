package com.allianceever.projectERP.controller;

import com.allianceever.projectERP.model.dto.PaymentDto;
import com.allianceever.projectERP.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@ComponentScan(basePackages = "com.allianceever.projectERP")
public class PaymentController {


    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/addPayment")
    public ResponseEntity<Void> addPayment(@RequestBody PaymentDto request) {
        paymentService.create(request); // Implement this method
        return ResponseEntity.ok().build();
    }


    // Build Delete Employee REST API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id")  Long id){

        paymentService.delete(id);
        return ResponseEntity.ok("payment deleted successfully!");
    }
}

