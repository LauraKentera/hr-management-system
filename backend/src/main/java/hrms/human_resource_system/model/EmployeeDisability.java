package main.java.hrms.human_resource_system.model;

import java.time.LocalDate;

public class EmployeeDisability {

    private int employeeDisabilityId;
    private int employeeId;
    private int disabilityCategoryId;
    private String officialCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String description;
    private Integer percentage;
    private boolean isActive;

    public EmployeeDisability() {}

    public EmployeeDisability(int employeeDisabilityId, int employeeId, int disabilityCategoryId,
                              String officialCode, LocalDate fromDate, LocalDate toDate,
                              String description, Integer percentage, boolean isActive) {
        this.employeeDisabilityId = employeeDisabilityId;
        this.employeeId = employeeId;
        this.disabilityCategoryId = disabilityCategoryId;
        this.officialCode = officialCode;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
        this.percentage = percentage;
        this.isActive = isActive;
    }

    public int getEmployeeDisabilityId() { return employeeDisabilityId; }
    public void setEmployeeDisabilityId(int id) { this.employeeDisabilityId = id; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getDisabilityCategoryId() { return disabilityCategoryId; }
    public void setDisabilityCategoryId(int disabilityCategoryId) { this.disabilityCategoryId = disabilityCategoryId; }

    public String getOfficialCode() { return officialCode; }
    public void setOfficialCode(String officialCode) { this.officialCode = officialCode; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPercentage() { return percentage; }
    public void setPercentage(Integer percentage) { this.percentage = percentage; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}

