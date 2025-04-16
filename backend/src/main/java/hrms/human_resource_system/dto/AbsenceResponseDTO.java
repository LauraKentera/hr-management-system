package main.java.hrms.human_resource_system.dto;


import java.time.LocalDate;

public class AbsenceResponseDTO {
    private int absenceId;
    private int employeeId;
    private String employeeName; // Optional: Full name for display
    private int absenceTypeId;
    private String absenceTypeName; // "Sick Leave", "Unpaid Leave", etc.
    private boolean isPaid;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;

    // Constructors
    public AbsenceResponseDTO() {}

    public AbsenceResponseDTO(int absenceId, int employeeId, String employeeName,
                              int absenceTypeId, String absenceTypeName, boolean isPaid,
                              LocalDate startDate, LocalDate endDate, String notes) {
        this.absenceId = absenceId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.absenceTypeId = absenceTypeId;
        this.absenceTypeName = absenceTypeName;
        this.isPaid = isPaid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    // Getters and Setters
    public int getAbsenceId() { return absenceId; }
    public void setAbsenceId(int absenceId) { this.absenceId = absenceId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public int getAbsenceTypeId() { return absenceTypeId; }
    public void setAbsenceTypeId(int absenceTypeId) { this.absenceTypeId = absenceTypeId; }

    public String getAbsenceTypeName() { return absenceTypeName; }
    public void setAbsenceTypeName(String absenceTypeName) { this.absenceTypeName = absenceTypeName; }

    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
