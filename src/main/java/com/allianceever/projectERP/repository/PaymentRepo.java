package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

    List<Payment> findAll();


}
