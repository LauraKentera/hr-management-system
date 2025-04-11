package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.PayrollRequest;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.service.EmployeeService;
import main.java.hrms.human_resource_system.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllPayrolls() {
        try {
            List<Payroll> payrolls = payrollService.getAllPayrolls();
            return ResponseEntity.ok(payrolls);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving payrolls", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> addPayroll(@RequestBody Payroll payroll) {
        try {
            Payroll created = payrollService.addPayroll(payroll);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating payroll", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayroll(@PathVariable int id, @RequestBody Payroll payroll) {
        try {
            if (payroll.getPayrollId() != null && payroll.getPayrollId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }

            payroll.setPayrollId(id);
            Payroll updated = payrollService.updatePayroll(payroll);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating payroll", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayroll(@PathVariable int id) {
        try {
            payrollService.deletePayroll(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(e.getMessage(), 404));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse(e.getMessage(), 409));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error deleting payroll", 500));
        }
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
