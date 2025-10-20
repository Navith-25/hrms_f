package com.nibm.hr.hrms.controller;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.PerformanceReview;
import com.nibm.hr.hrms.service.EmployeeService;
import com.nibm.hr.hrms.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PerformanceController {

    @Autowired
    private PerformanceReviewService reviewService;

    @Autowired
    private EmployeeService employeeService;

    // --- ADMIN/HR ACTIONS ---

    /**
     * Show a list of all reviews for a specific employee.
     */
    @GetMapping("/admin/performance/list/{employeeId}")
    public String showEmployeeReviewList(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<PerformanceReview> reviews = reviewService.getReviewsForEmployee(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("reviews", reviews);

        return "admin_performance_list"; // -> admin_performance_list.html
    }

    /**
     * Show the form to create a new review for an employee.
     */
    @GetMapping("/admin/performance/new/{employeeId}")
    public String showNewReviewForm(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setReviewDate(LocalDate.now());

        model.addAttribute("review", review);
        model.addAttribute("employeeName", employee.getFirstName() + " " + employee.getLastName());

        return "admin_performance_form"; // -> admin_performance_form.html
    }

    /**
     * Show the form to edit an existing review.
     */
    @GetMapping("/admin/performance/edit/{reviewId}")
    public String showEditReviewForm(@PathVariable Long reviewId, Model model) {
        PerformanceReview review = reviewService.getReviewById(reviewId);

        model.addAttribute("review", review);
        model.addAttribute("employeeName", review.getEmployee().getFirstName() + " " + review.getEmployee().getLastName());

        return "admin_performance_form"; // -> admin_performance_form.html
    }

    /**
     * Save the new or updated performance review.
     */
    @PostMapping("/admin/performance/save")
    public String saveReview(@ModelAttribute("review") PerformanceReview review) {
        // The form binds 'employee.id'. We need to re-bind the full employee object.
        Employee employee = employeeService.getEmployeeById(review.getEmployee().getId());
        review.setEmployee(employee);

        reviewService.saveReview(review);

        // Redirect back to the list for that employee
        return "redirect:/admin/performance/list/" + review.getEmployee().getId();
    }

    // --- EMPLOYEE ACTIONS ---

    /**
     * Show a list of all reviews for the currently logged-in user.
     */
    @GetMapping("/performance")
    public String showMyReviews(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("myReviews", reviewService.getReviewsForEmployeeByUsername(username));
        return "my_performance_reviews"; // -> my_performance_reviews.html
    }
}