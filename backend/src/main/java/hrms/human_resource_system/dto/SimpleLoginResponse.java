package hrms.human_resource_system.dto;

public class SimpleLoginResponse {
    private String role;
    private int userId;

    public SimpleLoginResponse(String role, int userId) {
        this.role = role;
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }
}
