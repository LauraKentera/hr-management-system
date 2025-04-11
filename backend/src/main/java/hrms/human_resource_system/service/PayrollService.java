package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.repository.ContractDAO;
import main.java.hrms.human_resource_system.repository.EmployeeAbsenceDAO;
import main.java.hrms.human_resource_system.repository.EmployeeChangeDAO;
import main.java.hrms.human_resource_system.repository.PayrollDAO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PayrollService {

    private final EmployeeAbsenceDAO employeeAbsenceDAO = new EmployeeAbsenceDAO();
    private final EmployeeChangeDAO employeeChangeDAO = new EmployeeChangeDAO();
    private final ContractDAO contractDAO = new ContractDAO();
    private final PayrollDAO payrollDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
    }

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    // Get Payroll by ID
    public Payroll getById(int id) {
        return wrap(() -> payrollDAO.getById(id));
    }

    // Get all Payrolls
    public List<Payroll> getAll() {
        return wrap(payrollDAO::getAll);
    }

    // Get all Payrolls (duplicate method, consider removing if not needed)
    public List<Payroll> getAllPayrolls() {
        return wrap(payrollDAO::getAll);
    }

    // Add new Payroll
    public void addPayroll(Payroll payroll) {
        wrap(() -> {
            validatePayroll(payroll);
            payrollDAO.insert(payroll);
            return null;  // Return type is Void
        });
    }

    // Update existing Payroll
    public void updatePayroll(Payroll payroll) {
        wrap(() -> {
            validatePayroll(payroll);
            payrollDAO.update(payroll);
            return null;  // Return type is Void
        });
    }

    // Delete Payroll by ID
    public void deletePayroll(int payrollId) {
        wrap(() -> {
            payrollDAO.delete(payrollId);
            return null;  // Return type is Void
        });
    }

    // Validation logic for Payroll fields
    private void validatePayroll(Payroll payroll) {
        if (payroll.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("⛔ Invalid employee ID.");
        }

        if (payroll.getBaseSalary() == null || payroll.getBaseSalary().compareTo(new java.math.BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException("⛔ Salary must be greater than 0.");
        }

        if (payroll.getPeriodStart() == null || payroll.getPeriodEnd() == null) {
            throw new IllegalArgumentException("⛔ Payroll period start and end dates cannot be null.");
        }

        if (!isPayrollPeriodValid(payroll)) {
            throw new IllegalArgumentException("⛔ Payroll start date must be before end date.");
        }

        // Additional business rule checks (optional)
        // e.g., if salary should be checked against employee's contract type or position
    }

    // Optional validation method to check if the payroll period is valid
    public boolean isPayrollPeriodValid(Payroll payroll) {
        return payroll.getPeriodStart().isBefore(payroll.getPeriodEnd());
    }

    // Method to calculate net pay for an employee within a given date range
    public BigDecimal calculateNetPay(Employee employee, LocalDate from, LocalDate to) {
        BigDecimal baseSalary = getBaseSalary(employee, from, to);
        BigDecimal bonuses = getBonuses(employee, from, to);
        BigDecimal deductions = getDeductions(employee, from, to);
        BigDecimal benefits = getTaxableBenefits(employee, from, to);

        // Calculate net pay: base salary + bonuses - deductions + taxable benefits
        return baseSalary.add(bonuses).subtract(deductions).add(benefits);
    }

    // Helper method to get the base salary (EmployeeChange or Contract)
    private BigDecimal getBaseSalary(Employee employee, LocalDate from, LocalDate to) {
        // Check employee change history or contract to get the base salary during the given period
        // Assuming you will fetch it from the EmployeeChange or Contract
        // Example: Using contractDAO for demonstration
        BigDecimal baseSalary = contractDAO.getBaseSalary(employee.getId(), from, to);

        return baseSalary != null ? baseSalary : BigDecimal.ZERO;
    }

    // Helper method to get bonuses (if any)
    private BigDecimal getBonuses(Employee employee, LocalDate from, LocalDate to) {
        // Fetch bonuses from payroll records or other business logic
        // For now, we assume bonuses are part of the employee's compensation plan
        // Example: Just returning zero for simplicity
        return BigDecimal.ZERO;  // Placeholder: Fetch actual bonuses from relevant source
    }

    // Helper method to get deductions, including unpaid absences
    private BigDecimal getDeductions(Employee employee, LocalDate from, LocalDate to) {
        List<EmployeeAbsence> absences = employeeAbsenceDAO.getByEmployeeId(employee.getId());
        BigDecimal totalDeductions = BigDecimal.ZERO;

        // Deduct the unpaid absence days (calculate days and apply some formula if needed)
        for (EmployeeAbsence absence : absences) {
            if (!absence.getAbsenceType().isPaid()) {
                totalDeductions = totalDeductions.add(calculateAbsenceDeductions(absence));
            }
        }

        return totalDeductions;
    }

    // Helper method to calculate deductions for unpaid absences
    private BigDecimal calculateAbsenceDeductions(EmployeeAbsence absence) {
        // Assuming we calculate deductions based on the days of absence and employee's daily salary rate
        long absenceDays = absence.getStartDate().until(absence.getEndDate(), java.time.temporal.ChronoUnit.DAYS);
        BigDecimal dailySalary = getBaseSalary(absence.getEmployee(), absence.getStartDate(), absence.getEndDate()).divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP); // Assuming 30 days in a month
        return dailySalary.multiply(BigDecimal.valueOf(absenceDays));
    }

    // Helper method to get taxable benefits
    private BigDecimal getTaxableBenefits(Employee employee, LocalDate from, LocalDate to) {
        // Check benefits of the employee, if they are taxable and add them to the net pay calculation
        // Example: Returning zero for simplicity
        return BigDecimal.ZERO;  // Placeholder: Implement actual taxable benefits logic
    }
}
