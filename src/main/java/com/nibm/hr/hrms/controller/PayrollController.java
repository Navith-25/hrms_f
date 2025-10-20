package com.nibm.hr.hrms.controller;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.Payroll;
import com.nibm.hr.hrms.model.Payslip;
import com.nibm.hr.hrms.service.EmployeeService;
import com.nibm.hr.hrms.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// --- ADD THIS IMPORT ---
import org.springframework.security.core.Authentication;
// --- END IMPORT ---

import java.math.BigDecimal;
import java.security.Principal; // We can still use this for simple username
import java.time.LocalDate;

@Controller
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeService employeeService;

    // --- ADMIN/HR ACTIONS ---

    @GetMapping("/admin/payroll/manage/{employeeId}")
    public String showManagePayrollPage(@PathVariable Long employeeId, Model model) {
        Payroll payroll = payrollService.getPayrollSettings(employeeId);
        model.addAttribute("payroll", payroll);
        model.addAttribute("employee", payroll.getEmployee());
        return "admin_payroll_manage";
    }

    /**
     * Save the updated payroll settings.
     */
    @PostMapping("/admin/payroll/save")
    public String savePayrollSettings(@ModelAttribute Payroll payroll,
                                      @RequestParam("employeeId") Long employeeId) { // <-- FIX #2A: Get employeeId

        // --- THIS IS THE FIX (FIX #2B) ---
        // We find the employee using the reliable 'employeeId' parameter from the form
        Employee employee = employeeService.getEmployeeById(employeeId);
        // --- END FIX ---

        payroll.setEmployee(employee);
        payroll.setId(employeeId); // <-- FIX #2C: Set the Payroll ID to match the Employee ID

        payrollService.savePayrollSettings(payroll);
        return "redirect:/"; // Redirect to homepage after saving
    }

    @GetMapping("/admin/payroll/run/{employeeId}")
    public String showRunPayrollPage(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("payDate", LocalDate.now());
        model.addAttribute("bonus", BigDecimal.ZERO);
        return "admin_payroll_run";
    }

    @PostMapping("/admin/payroll/run")
    public String runPayroll(@RequestParam Long employeeId,
                             @RequestParam LocalDate payDate,
                             @RequestParam(required = false) BigDecimal bonus) {

        payrollService.generatePayslip(employeeId, payDate, bonus);
        return "redirect:/";
    }

    // --- EMPLOYEE ACTIONS ---

    @GetMapping("/payslips")
    public String showMyPayslips(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("myPayslips", payrollService.getPayslipsForEmployee(username));
        return "my_payslips";
    }

    /**
     * Show the detailed view of a single payslip.
     */
    @GetMapping("/payslip/{id}")
    public String showPayslipDetail(@PathVariable Long id, Model model,
                                    Authentication authentication) { // <-- FIX #3A: Use Authentication

        Payslip payslip = payrollService.getPayslipById(id);
        String username = authentication.getName(); // Get username from Authentication

        // --- THIS IS THE FIX (FIX #3B) ---
        // Check if the user owns the payslip OR has an ADMIN/HR role
        boolean isOwner = payslip.getEmployee().getEmail().equals(username);
        boolean isAdminOrHr = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_HR"));

        if (!isOwner && !isAdminOrHr) {
            // If not owner AND not admin/hr, redirect
            return "redirect:/payslips";
        }
        // --- END FIX ---

        model.addAttribute("payslip", payslip);
        return "payslip_detail";
    }
}