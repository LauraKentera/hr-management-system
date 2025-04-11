package main.java.hrms.human_resource_system.dto;

public class EmployeeDisabilityResponseDTO {
    private int id;
    private int employeeId;
    private int disabilityCategoryId;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getDisabilityCategoryId() {
        return disabilityCategoryId;
    }

    public void setDisabilityCategoryId(int disabilityCategoryId) {
        this.disabilityCategoryId = disabilityCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

