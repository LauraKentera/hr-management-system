package hrms.human_resource_system.dto;

import java.util.Map;

public class DashboardDTO {
    private long totalEmployees;
    private long activeContracts;
    private long pendingBenefits;
    private long employeeAbsencesToday;
    private double averageSalary;
    private double employeeRetentionRate;
    private double benefitsUtilization;
    private long contractExpirations;
    private String nextPayCycle;
    private Map<String, Double> netPayByEmployee;

    public Map<String, Double> getNetPayByEmployee() {
        return netPayByEmployee;
    }

    public void setNetPayByEmployee(Map<String, Double> netPayByEmployee) {
        this.netPayByEmployee = netPayByEmployee;
    }

    // Getters and Setters
    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(long activeContracts) {
        this.activeContracts = activeContracts;
    }

    public long getPendingBenefits() {
        return pendingBenefits;
    }

    public void setPendingBenefits(long pendingBenefits) {
        this.pendingBenefits = pendingBenefits;
    }

    public long getEmployeeAbsencesToday() {
        return employeeAbsencesToday;
    }

    public void setEmployeeAbsencesToday(long employeeAbsencesToday) {
        this.employeeAbsencesToday = employeeAbsencesToday;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public double getEmployeeRetentionRate() {
        return employeeRetentionRate;
    }

    public void setEmployeeRetentionRate(double employeeRetentionRate) {
        this.employeeRetentionRate = employeeRetentionRate;
    }

    public double getBenefitsUtilization() {
        return benefitsUtilization;
    }

    public void setBenefitsUtilization(double benefitsUtilization) {
        this.benefitsUtilization = benefitsUtilization;
    }

    public long getContractExpirations() {
        return contractExpirations;
    }

    public void setContractExpirations(long contractExpirations) {
        this.contractExpirations = contractExpirations;
    }

    public String getNextPayCycle() {
        return nextPayCycle;
    }

    public void setNextPayCycle(String nextPayCycle) {
        this.nextPayCycle = nextPayCycle;
    }
}
