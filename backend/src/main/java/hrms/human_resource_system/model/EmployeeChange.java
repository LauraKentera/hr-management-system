package main.java.hrms.human_resource_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeChange {

    private Integer changeId;
    private int employeeId;
    private LocalDate changeDate;
    private Integer oldPositionId;
    private int newPositionId;
    private BigDecimal oldSalary;
    private BigDecimal newSalary;

    public EmployeeChange() {}

    public EmployeeChange(Integer changeId, int employeeId, LocalDate changeDate,
                          Integer oldPositionId, int newPositionId,
                          BigDecimal oldSalary, BigDecimal newSalary) {
        this.changeId = changeId;
        this.employeeId = employeeId;
        this.changeDate = changeDate;
        this.oldPositionId = oldPositionId;
        this.newPositionId = newPositionId;
        this.oldSalary = oldSalary;
        this.newSalary = newSalary;
    }

    public Integer getChangeId() { return changeId; }
    public void setChangeId(Integer changeId) { this.changeId = changeId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public LocalDate getChangeDate() { return changeDate; }
    public void setChangeDate(LocalDate changeDate) { this.changeDate = changeDate; }

    public Integer getOldPositionId() { return oldPositionId; }
    public void setOldPositionId(Integer oldPositionId) { this.oldPositionId = oldPositionId; }

    public int getNewPositionId() { return newPositionId; }
    public void setNewPositionId(int newPositionId) { this.newPositionId = newPositionId; }

    public BigDecimal getOldSalary() { return oldSalary; }
    public void setOldSalary(BigDecimal oldSalary) { this.oldSalary = oldSalary; }

    public BigDecimal getNewSalary() { return newSalary; }
    public void setNewSalary(BigDecimal newSalary) { this.newSalary = newSalary; }
}

