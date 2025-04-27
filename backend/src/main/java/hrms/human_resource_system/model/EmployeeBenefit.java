package hrms.human_resource_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeBenefit {
    private Integer employeeBenefitId;
    private Employee employee;
    private Benefit benefit;
    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean useStandardAmount;
    private BigDecimal amount;
    private BigDecimal coefficient;
    private String description;
    private boolean isActive;

    public EmployeeBenefit() {
    }

    public EmployeeBenefit(int employeeBenefitId, Employee employee, Benefit benefit, LocalDate fromDate,
            LocalDate toDate, boolean useStandardAmount, BigDecimal amount, BigDecimal coefficient,
            String description, boolean isActive) {
        this.employeeBenefitId = employeeBenefitId;
        this.employee = employee;
        this.benefit = benefit;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.useStandardAmount = useStandardAmount;
        this.amount = amount;
        this.coefficient = coefficient;
        this.description = description;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Integer getId() {
        return employeeBenefitId;
    }
    
    public void setId(Integer id) {
        this.employeeBenefitId = id;
    }
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public boolean isUseStandardAmount() {
        return useStandardAmount;
    }

    public void setUseStandardAmount(boolean useStandardAmount) {
        this.useStandardAmount = useStandardAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setBenefit(BenefitItem benefit) {

    }
}
