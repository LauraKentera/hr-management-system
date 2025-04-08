package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.repository.PayrollDAO;

import java.util.List;

public class PayrollService {

    private final PayrollDAO payrollDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
    }

    public List<Payroll> getAllPayrolls() {
        return payrollDAO.getAll();
    }

    public void addPayroll(Payroll payroll) {
        payrollDAO.insert(payroll);
    }

    public void updatePayroll(Payroll payroll) {
        payrollDAO.update(payroll);
    }

    public void deletePayroll(int payrollId) {
        payrollDAO.delete(payrollId);
    }
}