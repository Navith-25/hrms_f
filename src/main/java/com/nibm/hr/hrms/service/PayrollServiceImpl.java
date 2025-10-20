package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.Payroll;
import com.nibm.hr.hrms.model.Payslip;
import com.nibm.hr.hrms.repository.EmployeeRepository;
import com.nibm.hr.hrms.repository.PayrollRepository;
import com.nibm.hr.hrms.repository.PayslipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private PayslipRepository payslipRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Simple flat tax rate for demonstration (e.g., 10%)
    private static final BigDecimal TAX_RATE = new BigDecimal("0.10");

    @Override
    public Payroll getPayrollSettings(Long employeeId) {
        // Find by ID, or create a new one if it doesn't exist
        return payrollRepository.findById(employeeId).orElseGet(() -> {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            Payroll newPayroll = new Payroll();
            newPayroll.setEmployee(employee);
            return newPayroll;
        });
    }

    @Override
    public Payroll savePayrollSettings(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    @Override
    public Payslip generatePayslip(Long employeeId, LocalDate payDate, BigDecimal bonus) {
        Payroll settings = getPayrollSettings(employeeId);
        Employee employee = settings.getEmployee();

        // Get salary components
        BigDecimal basePay = settings.getBaseSalary();
        BigDecimal deductions = settings.getStandardDeductions();
        if (bonus == null) {
            bonus = BigDecimal.ZERO;
        }

        // Calculate Gross Pay
        BigDecimal grossPay = basePay.add(bonus);

        // Calculate Tax (e.g., 10% of gross pay)
        BigDecimal tax = grossPay.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);

        // Calculate Total Deductions
        BigDecimal totalDeductions = deductions.add(tax);

        // Calculate Net Pay
        BigDecimal netPay = grossPay.subtract(totalDeductions);

        // Create and save the payslip
        Payslip payslip = new Payslip();
        payslip.setEmployee(employee);
        payslip.setPayDate(payDate);
        payslip.setBasePay(basePay);
        payslip.setBonus(bonus);
        payslip.setTax(tax);
        payslip.setTotalDeductions(totalDeductions);
        payslip.setNetPay(netPay);

        return payslipRepository.save(payslip);
    }

    @Override
    public List<Payslip> getPayslipsForEmployee(String username) {
        Employee employee = employeeRepository.findByEmail(username);
        if (employee == null) {
            return List.of();
        }
        return payslipRepository.findByEmployee(employee);
    }

    @Override
    public Payslip getPayslipById(Long payslipId) {
        return payslipRepository.findById(payslipId)
                .orElseThrow(() -> new RuntimeException("Payslip not found"));
    }
}