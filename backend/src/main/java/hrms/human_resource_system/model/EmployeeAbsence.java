package hrms.human_resource_system.model;

import java.time.LocalDate;

public class EmployeeAbsence {

    private Integer absenceId;
    private int employeeId;
    private int absenceTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
    private String status;  // New field for status
    private Integer approvedBy;  // New field for approvedBy
    private AbsenceType absenceType;
    private Employee employee;

    public EmployeeAbsence() {
    }

    public EmployeeAbsence(Integer absenceId, int employeeId, int absenceTypeId,
                           LocalDate startDate, LocalDate endDate, String notes, String status, Integer approvedBy) {
        this.absenceId = absenceId;
        this.employeeId = employeeId;
        this.absenceTypeId = absenceTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.status = status;
        this.approvedBy = approvedBy;
    }

    // Getters and Setters
    public Integer getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(Integer absenceId) {
        this.absenceId = absenceId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAbsenceTypeId() {
        return absenceTypeId;
    }

    public void setAbsenceTypeId(int absenceTypeId) {
        this.absenceTypeId = absenceTypeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;  // Getter for status
    }

    public void setStatus(String status) {
        this.status = status;  // Setter for status
    }

    public Integer getApprovedBy() {
        return approvedBy;  // Getter for approvedBy
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;  // Setter for approvedBy
    }

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
