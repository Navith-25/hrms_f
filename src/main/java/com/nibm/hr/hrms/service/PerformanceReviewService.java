package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.PerformanceReview;
import java.util.List;

public interface PerformanceReviewService {

    // Get all reviews for a specific employee
    List<PerformanceReview> getReviewsForEmployee(Long employeeId);

    // Get all reviews for the logged-in employee
    List<PerformanceReview> getReviewsForEmployeeByUsername(String username);

    // Save a new or updated review
    PerformanceReview saveReview(PerformanceReview review);

    // Get a single review by its ID (for editing)
    PerformanceReview getReviewById(Long reviewId);
}