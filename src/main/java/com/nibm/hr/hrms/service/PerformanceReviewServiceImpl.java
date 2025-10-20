package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.PerformanceReview;
import com.nibm.hr.hrms.repository.EmployeeRepository;
import com.nibm.hr.hrms.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository reviewRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<PerformanceReview> getReviewsForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return reviewRepository.findByEmployee(employee);
    }

    @Override
    public List<PerformanceReview> getReviewsForEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByEmail(username);
        if (employee == null) {
            return List.of();
        }
        return reviewRepository.findByEmployee(employee);
    }

    @Override
    public PerformanceReview saveReview(PerformanceReview review) {
        // You could add logic here to calculate overall rating
        return reviewRepository.save(review);
    }

    @Override
    public PerformanceReview getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}