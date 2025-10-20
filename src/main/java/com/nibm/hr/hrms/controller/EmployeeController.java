package com.nibm.hr.hrms.controller;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Display list of employees
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "index"; // This will look for index.html in /templates
    }

    // Show form to add a new employee
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee"; // Looks for new_employee.html
    }

    // Save employee to database
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    // Show form to update an employee
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        // Get employee from the service
        Employee employee = employeeService.getEmployeeById(id);

        // Set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee"; // Looks for update_employee.html
    }

    // Delete an employee
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }
}
