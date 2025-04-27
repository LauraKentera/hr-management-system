package hrms.human_resource_system.controller;

import hrms.human_resource_system.model.ApprovalRequest;
import hrms.human_resource_system.service.ApprovalRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createApprovalRequest(@RequestBody ApprovalRequest approvalRequest) {
        try {
            approvalRequestService.createApprovalRequest(approvalRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Approval request created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Approve or reject an approval request
    @PutMapping("/{id}")
    public ResponseEntity<String> updateApprovalRequestStatus(@PathVariable int id, @RequestParam String status, @RequestParam int approvedBy) {
        try {
            approvalRequestService.updateApprovalRequestStatus(id, status, approvedBy);
            return ResponseEntity.ok("Approval request updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Get all pending approval requests
    @GetMapping
    public ResponseEntity<List<ApprovalRequest>> getPendingApprovalRequests() {
        List<ApprovalRequest> requests = approvalRequestService.getPendingApprovalRequests();
        return ResponseEntity.ok(requests);
    }
}
