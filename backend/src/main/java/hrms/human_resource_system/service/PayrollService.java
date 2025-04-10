package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.repository.PayrollDAO;

import java.util.List;

public class PayrollService {

    private final PayrollDAO payrollDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
    }

    public Payroll getById(int id) {
        return payrollDAO.getById(id);
    }

    public List<Payroll> getAll() {
        return payrollDAO.getAll();
    }

    public List<Payroll> getAllPayrolls() {
        return payrollDAO.getAll();
    }

    public void addPayroll(Payroll payroll) {
        validatePayroll(payroll);
        payrollDAO.insert(payroll);
    }

    public void updatePayroll(Payroll payroll) {
        validatePayroll(payroll);
        payrollDAO.update(payroll);
    }

    public void deletePayroll(int payrollId) {
        payrollDAO.delete(payrollId);
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
