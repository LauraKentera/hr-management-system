package hrms.human_resource_system.exception;

import java.time.LocalDateTime;

public class CustomErrorResponse {
    private String error;
    private int status;
    private LocalDateTime timestamp;

    public CustomErrorResponse(String error, int status) {
        this.error = error;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getError() { return error; }
    public int getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
