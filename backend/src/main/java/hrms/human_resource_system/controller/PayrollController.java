package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.service.PayrollService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController() {
        this.payrollService = new PayrollService();
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
}
