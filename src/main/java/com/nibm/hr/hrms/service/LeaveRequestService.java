package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.LeaveRequest;
import java.util.List;

public interface LeaveRequestService {

    // Get all requests submitted by a specific employee
    List<LeaveRequest> getRequestsByEmployeeUsername(String username);

    // Get all requests that are still PENDING (for admin)
    List<LeaveRequest> getAllPendingRequests();

    // Submit a new request
    void createLeaveRequest(LeaveRequest leaveRequest, String username);

    // Admin actions
    void approveRequest(Long id);
    void rejectRequest(Long id);
}