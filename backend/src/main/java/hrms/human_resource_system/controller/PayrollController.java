package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.PayrollRequest;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.service.EmployeeService;
import main.java.hrms.human_resource_system.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    private final PayrollService payrollService;
    private final EmployeeService employeeService;

    @Autowired
    public PayrollController(PayrollService payrollService, EmployeeService employeeService) {
        this.payrollService = payrollService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Payroll>> getAllPayrolls() {
        List<Payroll> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok(payrolls);
    }

    @PostMapping
    public ResponseEntity<String> addPayroll(@RequestBody Payroll payroll) {
        payrollService.addPayroll(payroll);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Payroll created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePayroll(@PathVariable int id, @RequestBody Payroll payroll) {
        payroll.setPayrollId(id); // ensure ID from URL is used
        payrollService.updatePayroll(payroll);
        return ResponseEntity.ok("🔄 Payroll updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayroll(@PathVariable int id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.ok("🗑️ Payroll with ID " + id + " deleted.");
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePayroll(@RequestBody PayrollRequest request) {
        // Assuming request includes employeeId, startDate, endDate, and other details
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());

        // Calculate net pay using the calculateNetPay method
        BigDecimal netPay = payrollService.calculateNetPay(employee, request.getStartDate(), request.getEndDate());

        // Generate Payroll object and save it (save payroll record logic here)
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(request.getEmployeeId());
        payroll.setPeriodStart(request.getStartDate());
        payroll.setPeriodEnd(request.getEndDate());
        payroll.setNetPay(netPay);
        payrollService.addPayroll(payroll);

        return ResponseEntity.ok("✅ Payroll generated for employee " + request.getEmployeeId());
    }
}
