package com.nibm.hr.hrms.repository;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {

    // Find all payslips for a specific employee
    List<Payslip> findByEmployee(Employee employee);
}