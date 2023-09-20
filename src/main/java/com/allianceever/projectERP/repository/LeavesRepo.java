package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.Holiday;
import com.allianceever.projectERP.model.entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeavesRepo extends JpaRepository<Leaves,Long> {

    Leaves findByLeavesID(Integer leavesID);


    //@Query(value = "SELECT * FROM holiday ORDER BY STR_TO_DATE(holiday_date, '%Y-%m-%d') ASC", nativeQuery = true)
    @Query(value = "SELECT * FROM leaves ORDER BY STR_TO_DATE(start_date, '%d/%m/%Y') ASC", nativeQuery = true)
    List<Leaves> findAllOrderByDate();

    void deleteByLeavesID(Integer leavesID);

    @Query(value = "SELECT * FROM leaves WHERE username = :username ORDER BY STR_TO_DATE(start_date, '%d/%m/%Y') ASC", nativeQuery = true)
    List<Leaves> getAllLeavesByUsernameOrderedByDate(@Param("username") String username);
}
