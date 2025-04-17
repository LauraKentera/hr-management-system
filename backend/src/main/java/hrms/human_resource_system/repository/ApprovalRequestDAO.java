package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.ApprovalRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApprovalRequestDAO {

    private final JdbcTemplate jdbcTemplate;

    public ApprovalRequestDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert a new approval request
    public void insert(ApprovalRequest approvalRequest) {
        String sql = "INSERT INTO ApprovalRequest (request_type, employee_id, related_id, status, requested_by, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, approvalRequest.getRequestType(), approvalRequest.getEmployeeId(),
                            approvalRequest.getRelatedId(), approvalRequest.getStatus(), approvalRequest.getRequestedBy(),
                            approvalRequest.getTimestamp());
    }

    // Update the approval request status
    public void update(ApprovalRequest approvalRequest) {
        String sql = "UPDATE ApprovalRequest SET status = ?, approved_by = ?, timestamp = ? WHERE request_id = ?";
        jdbcTemplate.update(sql, approvalRequest.getStatus(), approvalRequest.getApprovedBy(),
                            approvalRequest.getTimestamp(), approvalRequest.getRequestId());
    }

    // Retrieve an approval request by ID
    public Optional<ApprovalRequest> getById(int requestId) {
        String sql = "SELECT * FROM ApprovalRequest WHERE request_id = ?";
        try {
            ApprovalRequest approvalRequest = jdbcTemplate.queryForObject(sql, new Object[]{requestId}, (rs, rowNum) -> {
                ApprovalRequest request = new ApprovalRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setRequestType(rs.getString("request_type"));
                request.setEmployeeId(rs.getInt("employee_id"));
                request.setRelatedId(rs.getInt("related_id"));
                request.setStatus(rs.getString("status"));
                request.setRequestedBy(rs.getInt("requested_by"));
                request.setApprovedBy(rs.getInt("approved_by"));
                request.setTimestamp(rs.getTimestamp("timestamp"));
                return request;
            });
            return Optional.ofNullable(approvalRequest); // Wrap in Optional to handle the case where result is null
        } catch (Exception e) {
            return Optional.empty(); // Return an empty Optional if no result found or error occurred
        }
    }

    // Get all approval requests with a specific status
    public List<ApprovalRequest> getByStatus(String status) {
        String sql = "SELECT * FROM ApprovalRequest WHERE status = ?";
        return jdbcTemplate.query(sql, new Object[]{status}, (rs, rowNum) -> {
            ApprovalRequest approvalRequest = new ApprovalRequest();
            approvalRequest.setRequestId(rs.getInt("request_id"));
            approvalRequest.setRequestType(rs.getString("request_type"));
            approvalRequest.setEmployeeId(rs.getInt("employee_id"));
            approvalRequest.setRelatedId(rs.getInt("related_id"));
            approvalRequest.setStatus(rs.getString("status"));
            approvalRequest.setRequestedBy(rs.getInt("requested_by"));
            approvalRequest.setApprovedBy(rs.getInt("approved_by"));
            approvalRequest.setTimestamp(rs.getTimestamp("timestamp"));
            return approvalRequest;
        });
    }
}
