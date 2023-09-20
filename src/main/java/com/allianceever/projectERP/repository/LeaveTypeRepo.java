package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveTypeRepo extends JpaRepository<LeaveType,Long> {

    LeaveType findByLeaveName(String leaveName);

    void deleteByLeaveName(String leaveName);

    @Query("SELECT lt FROM LeaveType lt WHERE lt.username = :username")
    List<LeaveType> getAllLeaveTypeByUsername(@Param("username") String username);

}
