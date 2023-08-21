package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.EstimatesInvoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstimatesInvoicesRepo extends JpaRepository<EstimatesInvoices,Long> {
   // @Query(value = "SELECT * FROM holiday ORDER BY STR_TO_DATE(holiday_date, '%d/%m/%Y') ASC", nativeQuery = true)
    //List<EstimatesInvoicesRepo> findAllOrderByDate();

    EstimatesInvoices findById(Integer id);

    void deleteById(Integer id);




}
