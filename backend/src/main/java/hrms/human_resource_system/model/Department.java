package hrms.human_resource_system.model;

public class Department {
    private int departmentId;
    private String name;
    private Integer managerId; // Foreign key to Employee

    public Department() {}

    public Department(int departmentId, String name, Integer managerId) {
        this.departmentId = departmentId;
        this.name = name;
        this.managerId = managerId;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
}

