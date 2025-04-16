package main.java.hrms.human_resource_system.dto;

public class EmployeeBenefitRequestDTO {
    private int employeeId;
    private int benefitItemId;
    private String startDate;
    private String endDate;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getBenefitItemId() {
        return benefitItemId;
    }

    public void setBenefitItemId(int benefitItemId) {
        this.benefitItemId = benefitItemId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
