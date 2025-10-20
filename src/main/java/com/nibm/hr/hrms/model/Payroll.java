package com.nibm.hr.hrms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_settings")
public class Payroll {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // This maps the 'id' field as both Primary Key and Foreign Key
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "base_salary", precision = 10, scale = 2)
    private BigDecimal baseSalary;

    // Standard monthly deductions (e.g., benefits)
    @Column(precision = 10, scale = 2)
    private BigDecimal standardDeductions;

    // Constructors
    public Payroll() {
        this.baseSalary = BigDecimal.ZERO;
        this.standardDeductions = BigDecimal.ZERO;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getStandardDeductions() {
        return standardDeductions;
    }

    public void setStandardDeductions(BigDecimal standardDeductions) {
        this.standardDeductions = standardDeductions;
    }
}