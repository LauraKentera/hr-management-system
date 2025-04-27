package hrms.human_resource_system.dto;

public class AuthResponseDTO {
    private String token;        // JWT or session token
    private String username;     // Authenticated user's username
    private String role;         // e.g., "ADMIN", "EMPLOYEE"
    private long expiresIn;      // Optional: token expiry timestamp (e.g., in ms)

    // Constructors
    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, String username, String role, long expiresIn) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
