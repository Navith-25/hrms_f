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

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeService employeeService;

    // --- ADMIN/HR ACTIONS ---

    /**
     * Show the page to manage payroll settings for a specific employee.
     */
    @GetMapping("/admin/payroll/manage/{employeeId}")
    public String showManagePayrollPage(@PathVariable Long employeeId, Model model) {
        Payroll payroll = payrollService.getPayrollSettings(employeeId);
        model.addAttribute("payroll", payroll);
        model.addAttribute("employee", payroll.getEmployee());
        return "admin_payroll_manage"; // -> admin_payroll_manage.html
    }

    /**
     * Save the updated payroll settings.
     */
    @PostMapping("/admin/payroll/save")
    public String savePayrollSettings(@ModelAttribute Payroll payroll) {
        // We need to re-attach the employee to the payroll object
        Employee employee = employeeService.getEmployeeById(payroll.getId());
        payroll.setEmployee(employee);

        payrollService.savePayrollSettings(payroll);
        return "redirect:/"; // Redirect to homepage after saving
    }

    /**
     * Show the page to "run" payroll (generate a payslip) for an employee.
     */
    @GetMapping("/admin/payroll/run/{employeeId}")
    public String showRunPayrollPage(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        // Set defaults for the form
        model.addAttribute("payDate", LocalDate.now());
        model.addAttribute("bonus", BigDecimal.ZERO);
        return "admin_payroll_run"; // -> admin_payroll_run.html
    }

    /**
     * Handle the form submission to run payroll.
     */
    @PostMapping("/admin/payroll/run")
    public String runPayroll(@RequestParam Long employeeId,
                             @RequestParam LocalDate payDate,
                             @RequestParam(required = false) BigDecimal bonus) {

        payrollService.generatePayslip(employeeId, payDate, bonus);
        // Redirect to the employee's payslip page so they can see it
        return "redirect:/";
    }

    // --- EMPLOYEE ACTIONS ---

    /**
     * Show a list of all payslips for the currently logged-in user.
     */
    @GetMapping("/payslips")
    public String showMyPayslips(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("myPayslips", payrollService.getPayslipsForEmployee(username));
        return "my_payslips"; // -> my_payslips.html
    }

    /**
     * Show the detailed view of a single payslip.
     */
    @GetMapping("/payslip/{id}")
    public String showPayslipDetail(@PathVariable Long id, Model model, Principal principal) {
        Payslip payslip = payrollService.getPayslipById(id);

        // Security Check: Ensure the user is viewing their *own* payslip
        String username = principal.getName();
        if (!payslip.getEmployee().getEmail().equals(username)) {
            // Or has HR/Admin role
            // For now, just redirect if not their own
            // (A better check would use Spring Security)
            return "redirect:/payslips";
        }

        model.addAttribute("payslip", payslip);
        return "payslip_detail"; // -> payslip_detail.html
    }
}