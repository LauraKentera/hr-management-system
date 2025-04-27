package hrms.human_resource_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmploymentContract {
    private int contractId;
    private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int positionId;
    private BigDecimal salary;
    private String contractType;
    private LocalDate signedDate;
    private String documentPath;

    // Full constructor
    public EmploymentContract(int contractId, int employeeId, LocalDate startDate, LocalDate endDate,
                              int positionId, BigDecimal salary, String contractType,
                              LocalDate signedDate, String documentPath) {
        this.contractId = contractId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.positionId = positionId;
        this.salary = salary;
        this.contractType = contractType;
        this.signedDate = signedDate;
        this.documentPath = documentPath;
    }

    // Getters
    public int getContractId() {
        return contractId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPositionId() {
        return positionId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getContractType() {
        return contractType;
    }

    public LocalDate getSignedDate() {
        return signedDate;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    // Setters (optional, add only if you need them)
    public void setContractId(int contractId) {
        this.contractId = contractId;
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

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public void setSignedDate(LocalDate signedDate) {
        this.signedDate = signedDate;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
