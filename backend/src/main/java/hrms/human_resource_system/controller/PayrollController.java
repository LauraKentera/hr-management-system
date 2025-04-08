package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.service.PayrollService;

import java.util.List;

public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController() {
        this.payrollService = new PayrollService();
    }

    public List<Payroll> getAllPayrolls() {
        return payrollService.getAllPayrolls();
    }

    public void addPayroll(Payroll payroll) {
        payrollService.addPayroll(payroll);
    }

    public void updatePayroll(Payroll payroll) {
        payrollService.updatePayroll(payroll);
    }

    public void deletePayroll(int payrollId) {
        payrollService.deletePayroll(payrollId);
    }
}