package com.nibm.hr.hrms.repository;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.LeaveRequest;
import com.nibm.hr.hrms.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Find all requests for a specific employee
    List<LeaveRequest> findByEmployee(Employee employee);

    // Find all requests that have a specific status (e.g., "PENDING")
    List<LeaveRequest> findByStatus(LeaveStatus status);
}
