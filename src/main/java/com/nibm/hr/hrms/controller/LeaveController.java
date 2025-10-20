package com.nibm.hr.hrms.controller;

import com.nibm.hr.hrms.model.LeaveRequest;
import com.nibm.hr.hrms.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LeaveController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    /**
     * Show the leave page for the currently logged-in employee.
     * It displays their past requests and a form to submit a new one.
     */
    @GetMapping("/leave")
    public String showLeavePage(Model model, Principal principal) {
        // Get the username of the logged-in user
        String username = principal.getName();

        // Add their past requests to the model
        model.addAttribute("myLeaveRequests", leaveRequestService.getRequestsByEmployeeUsername(username));

        // Add a new empty request object for the form
        model.addAttribute("leaveRequest", new LeaveRequest());

        return "leave_request"; // -> leave_request.html
    }

    /**
     * Handle the submission of the new leave request form.
     */
    @PostMapping("/leave/request")
    public String submitLeaveRequest(@ModelAttribute("leaveRequest") LeaveRequest leaveRequest, Principal principal) {
        String username = principal.getName();
        leaveRequestService.createLeaveRequest(leaveRequest, username);
        return "redirect:/leave";
    }

    /**
     * Show the admin page for managing PENDING leave requests.
     */
    @GetMapping("/admin/leave")
    public String showAdminLeavePage(Model model) {
        model.addAttribute("pendingRequests", leaveRequestService.getAllPendingRequests());
        return "admin_leave_approval"; // -> admin_leave_approval.html
    }

    /**
     * Handle the "Approve" action from the admin page.
     */
    @GetMapping("/admin/leave/approve/{id}")
    public String approveLeaveRequest(@PathVariable("id") Long id) {
        leaveRequestService.approveRequest(id);
        return "redirect:/admin/leave";
    }

    /**
     * Handle the "Reject" action from the admin page.
     */
    @GetMapping("/admin/leave/reject/{id}")
    public String rejectLeaveRequest(@PathVariable("id") Long id) {
        leaveRequestService.rejectRequest(id);
        return "redirect:/admin/leave";
    }
}
