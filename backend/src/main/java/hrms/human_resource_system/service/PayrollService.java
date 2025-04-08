package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.PayrollDAO;

import java.util.List;

public class PayrollService {

    private final PayrollDAO payrollDAO;
    private final EmployeeDAO employeeDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
        this.employeeDAO = new EmployeeDAO(); 
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

    private void validatePayroll(Payroll payroll) {
        
        if (payroll.getBaseSalary().compareTo(new java.math.BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException("Base salary must be greater than 0.");
        }

        
        if (!employeeDAO.existsById(payroll.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + payroll.getEmployeeId() + " does not exist.");
        }
    }
}