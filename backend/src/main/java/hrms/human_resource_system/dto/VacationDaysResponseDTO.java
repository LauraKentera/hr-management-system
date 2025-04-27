package hrms.human_resource_system.dto;

public class VacationDaysResponseDTO {
    private int employeeId;
    private String employeeName;
    private int totalVacationDays;
    private int unpaidAbsenceDays;
    private int yearsOfService;

    public VacationDaysResponseDTO() {}

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getTotalVacationDays() {
        return totalVacationDays;
    }

    public void setTotalVacationDays(int totalVacationDays) {
        this.totalVacationDays = totalVacationDays;
    }

    public int getUnpaidAbsenceDays() {
        return unpaidAbsenceDays;
    }

    public void setUnpaidAbsenceDays(int unpaidAbsenceDays) {
        this.unpaidAbsenceDays = unpaidAbsenceDays;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }
}
