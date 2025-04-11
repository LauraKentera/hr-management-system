package main.java.hrms.human_resource_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractResponseDTO {
    private int contractId;
    private int employeeId;
    private String employeeName;     // Optional: Full name if needed
    private LocalDate startDate;
    private LocalDate endDate;
    private int positionId;
    private String positionName;     // Optional: Useful for display
    private BigDecimal salary;
    private String contractType;
    private LocalDate signedDate;
    private String documentPath;     // File link or filename

    // Constructors
    public ContractResponseDTO() {}

    public ContractResponseDTO(int contractId, int employeeId, String employeeName,
                               LocalDate startDate, LocalDate endDate,
                               int positionId, String positionName, BigDecimal salary,
                               String contractType, LocalDate signedDate, String documentPath) {
        this.contractId = contractId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.positionId = positionId;
        this.positionName = positionName;
        this.salary = salary;
        this.contractType = contractType;
        this.signedDate = signedDate;
        this.documentPath = documentPath;
    }

    // Getters and setters...
    public int getContractId() { return contractId; }
    public void setContractId(int contractId) { this.contractId = contractId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getPositionId() { return positionId; }
    public void setPositionId(int positionId) { this.positionId = positionId; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getContractType() { return contractType; }
    public void setContractType(String contractType) { this.contractType = contractType; }

    public LocalDate getSignedDate() { return signedDate; }
    public void setSignedDate(LocalDate signedDate) { this.signedDate = signedDate; }

    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }
}
