package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    void deleteEmployeeById(Long id);
}
