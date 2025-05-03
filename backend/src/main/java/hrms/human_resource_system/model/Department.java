package hrms.human_resource_system.model;

public class Department {
    private Integer departmentId; // Changed to Integer to allow null values
    private String name;
    private Integer managerId; // Foreign key to Employee

    public Department() {}

    public Department(Integer departmentId, String name, Integer managerId) {
        this.departmentId = departmentId;
        this.name = name;
        this.managerId = managerId;
    }

    public Integer getDepartmentId() { return departmentId; } // Return type changed to Integer
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; } // Parameter changed to Integer

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
}
