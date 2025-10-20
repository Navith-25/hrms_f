package com.nibm.hr.hrms.service;

import com.nibm.hr.hrms.model.Employee;
import com.nibm.hr.hrms.model.LeaveRequest;
import com.nibm.hr.hrms.model.LeaveStatus;
import com.nibm.hr.hrms.repository.EmployeeRepository;
import com.nibm.hr.hrms.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<LeaveRequest> getRequestsByEmployeeUsername(String username) {
        Employee employee = employeeRepository.findByEmail(username);
        if (employee == null) {
            // Handle case where employee profile doesn't exist for the user
            return List.of();
        }
        return leaveRequestRepository.findByEmployee(employee);
    }

    @Override
    public List<LeaveRequest> getAllPendingRequests() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING);
    }

    @Override
    public void createLeaveRequest(LeaveRequest leaveRequest, String username) {
        Employee employee = employeeRepository.findByEmail(username);
        if (employee == null) {
            throw new RuntimeException("Employee profile not found for user: " + username);
        }

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public void approveRequest(Long id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        request.setStatus(LeaveStatus.APPROVED);
        leaveRequestRepository.save(request);
    }

    @Override
    public void rejectRequest(Long id) {
        LeaveRequest request = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        request.setStatus(LeaveStatus.REJECTED);
        leaveRequestRepository.save(request);
    }
}
