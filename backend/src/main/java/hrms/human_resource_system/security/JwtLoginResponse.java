package hrms.human_resource_system.security;

public class JwtLoginResponse {
    private String token;
    private String type = "Bearer";
    private String role;
    private int userId;

    public JwtLoginResponse(String token, String role, int userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }
}
