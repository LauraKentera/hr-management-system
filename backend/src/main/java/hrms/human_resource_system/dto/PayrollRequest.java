package main.java.hrms.human_resource_system.dto;

import java.time.LocalDate;

public class PayrollRequest {
    private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
