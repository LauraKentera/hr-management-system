package main.java.hrms.human_resource_system.model;

import java.time.LocalDate;

public class EmployeeAbsence {

    private int absenceId;
    private int employeeId;
    private int absenceTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;

    public EmployeeAbsence() {}

    public EmployeeAbsence(int absenceId, int employeeId, int absenceTypeId,
                           LocalDate startDate, LocalDate endDate, String notes) {
        this.absenceId = absenceId;
        this.employeeId = employeeId;
        this.absenceTypeId = absenceTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public int getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(int absenceId) {
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
}
