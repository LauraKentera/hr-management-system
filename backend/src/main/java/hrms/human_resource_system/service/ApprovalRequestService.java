package hrms.human_resource_system.service;

import hrms.human_resource_system.model.ApprovalRequest;
import hrms.human_resource_system.repository.ApprovalRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalRequestService {

    private final ApprovalRequestDAO approvalRequestDAO;

    @Autowired
    public ApprovalRequestService(ApprovalRequestDAO approvalRequestDAO) {
        this.approvalRequestDAO = approvalRequestDAO;
    }

    // Create a new approval request
    public void createApprovalRequest(ApprovalRequest approvalRequest) {
        if (approvalRequest.getRequestType() == null || approvalRequest.getEmployeeId() == null) {
            throw new IllegalArgumentException("Request type and employee ID are required");
        }
        approvalRequest.setStatus("Pending");
        approvalRequestDAO.insert(approvalRequest);
    }

    // Approve or reject an approval request
    public void updateApprovalRequestStatus(int requestId, String status, int approvedBy) {
        Optional<ApprovalRequest> approvalRequest = approvalRequestDAO.getById(requestId);
        if (approvalRequest.isPresent()) {
            ApprovalRequest request = approvalRequest.get();
            if ("Pending".equals(request.getStatus())) {
                request.setStatus(status);
                request.setApprovedBy(approvedBy);
                approvalRequestDAO.update(request);
            } else {
                throw new IllegalArgumentException("Request is already processed.");
            }
        } else {
            throw new IllegalArgumentException("Approval request not found.");
        }
    }

    // Get all pending approval requests
    public List<ApprovalRequest> getPendingApprovalRequests() {
        return approvalRequestDAO.getByStatus("Pending");
    }
}
