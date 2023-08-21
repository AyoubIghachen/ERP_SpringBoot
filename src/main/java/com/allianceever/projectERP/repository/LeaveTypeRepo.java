package com.allianceever.projectERP.repository;

import com.allianceever.projectERP.model.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepo extends JpaRepository<LeaveType,Long> {

    LeaveType findByLeaveName(String leaveName);

    void deleteByLeaveName(String leaveName);


}
