package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.ApprovalRequest;
import main.java.hrms.human_resource_system.service.ApprovalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalRequestController {

    private final ApprovalRequestService approvalRequestService;

    @Autowired
    public ApprovalRequestController(ApprovalRequestService approvalRequestService) {
        this.approvalRequestService = approvalRequestService;
    }

    // Create a new approval request
    @PostMapping
    public HttpStatus createApprovalRequest(@RequestBody ApprovalRequest approvalRequest) {
        approvalRequestService.createApprovalRequest(approvalRequest);
        return HttpStatus.CREATED;
    }

    // Approve or reject an approval request
    @PutMapping("/{id}")
    public HttpStatus updateApprovalRequestStatus(@PathVariable int id, @RequestParam String status,
                                                  @RequestParam int approvedBy) {
        approvalRequestService.updateApprovalRequestStatus(id, status, approvedBy);
        return HttpStatus.OK;
    }

    // Get all pending approval requests
    @GetMapping
    public List<ApprovalRequest> getPendingApprovalRequests() {
        return approvalRequestService.getPendingApprovalRequests();
    }
}
