package com.nibm.hr.hrms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "performance_reviews")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    // KPI-based ratings (as integers 1-5)
    private Integer qualityOfWork;
    private Integer communication;
    private Integer productivity;
    private Integer dependability;

    @Lob // Use @Lob for large text fields
    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String areasForImprovement;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String managerComments;

    // Overall rating
    private Integer overallRating;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
    public Integer getQualityOfWork() { return qualityOfWork; }
    public void setQualityOfWork(Integer qualityOfWork) { this.qualityOfWork = qualityOfWork; }
    public Integer getCommunication() { return communication; }
    public void setCommunication(Integer communication) { this.communication = communication; }
    public Integer getProductivity() { return productivity; }
    public void setProductivity(Integer productivity) { this.productivity = productivity; }
    public Integer getDependability() { return dependability; }
    public void setDependability(Integer dependability) { this.dependability = dependability; }
    public String getStrengths() { return strengths; }
    public void setStrengths(String strengths) { this.strengths = strengths; }
    public String getAreasForImprovement() { return areasForImprovement; }
    public void setAreasForImprovement(String areasForImprovement) { this.areasForImprovement = areasForImprovement; }
    public String getManagerComments() { return managerComments; }
    public void setManagerComments(String managerComments) { this.managerComments = managerComments; }
    public Integer getOverallRating() { return overallRating; }
    public void setOverallRating(Integer overallRating) { this.overallRating = overallRating; }
}