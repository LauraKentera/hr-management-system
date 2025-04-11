package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.repository.PayrollDAO;

import java.util.List;
import java.util.function.Supplier;

public class PayrollService {

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
}
