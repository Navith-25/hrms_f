package com.nibm.hr.hrms.repository;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    // Find all reviews for a specific employee
    List<PerformanceReview> findByEmployee(Employee employee);
}