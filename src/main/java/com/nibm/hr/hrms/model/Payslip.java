package com.nibm.hr.hrms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payslips")
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "pay_date", nullable = false)
    private LocalDate payDate;

    @Column(name = "base_pay", precision = 10, scale = 2)
    private BigDecimal basePay;

    @Column(precision = 10, scale = 2)
    private BigDecimal bonus;

    @Column(name = "total_deductions", precision = 10, scale = 2)
    private BigDecimal totalDeductions;

    // As per proposal, tax integration
    @Column(precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(name = "net_pay", precision = 10, scale = 2)
    private BigDecimal netPay;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }
    public BigDecimal getBasePay() { return basePay; }
    public void setBasePay(BigDecimal basePay) { this.basePay = basePay; }
    public BigDecimal getBonus() { return bonus; }
    public void setBonus(BigDecimal bonus) { this.bonus = bonus; }
    public BigDecimal getTotalDeductions() { return totalDeductions; }
    public void setTotalDeductions(BigDecimal totalDeductions) { this.totalDeductions = totalDeductions; }
    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }
    public BigDecimal getNetPay() { return netPay; }
    public void setNetPay(BigDecimal netPay) { this.netPay = netPay; }
}
