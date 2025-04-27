package hrms.human_resource_system.dto;

public class UserResponseDTO {
    private int id;
    private String username;
    private String roleName;
    private Integer employeeId;

    public UserResponseDTO(int id, String username, String roleName, Integer employeeId) {
        this.id = id;
        this.username = username;
        this.roleName = roleName;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
