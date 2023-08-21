package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Expenses;
import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.model.entity.LeaveType;
import com.allianceever.projectERP.model.entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpensesRepo extends JpaRepository<Expenses,Long> {

    @Query(value = "SELECT * FROM expenses ORDER BY STR_TO_DATE(purchase_date, '%d/%m/%Y') ASC", nativeQuery = true)

    List<Expenses> findAllOrderByDate();
}
