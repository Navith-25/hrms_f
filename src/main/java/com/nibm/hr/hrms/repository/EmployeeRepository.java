package com.nibm.hr.hrms.repository;

import com.nibm.hr.hrms.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // This is the new method.
    // We assume the user's username is their employee email.
    Employee findByEmail(String email);
}