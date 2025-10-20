package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.Payroll;
import com.nibm.hr.hrms.model.Payslip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PayrollService {

    // Get payroll settings for an employee (creates if not exist)
    Payroll getPayrollSettings(Long employeeId);

    // Update payroll settings
    Payroll savePayrollSettings(Payroll payroll);

    // Generate a new payslip
    Payslip generatePayslip(Long employeeId, LocalDate payDate, BigDecimal bonus);

    // Get all payslips for a specific employee
    List<Payslip> getPayslipsForEmployee(String username);

    // Get a single payslip by its ID
    Payslip getPayslipById(Long payslipId);
}