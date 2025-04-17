package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.PayrollRequest;
import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
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

    // Get all payrolls
    @GetMapping
    public ResponseEntity<?> getAllPayrolls() {
        try {
            List<Payroll> payrolls = payrollService.getAll(); // Call the service method to get all payrolls
            return ResponseEntity.ok(payrolls);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving payrolls", 500));
        }
    }

    // Add new payroll
    @PostMapping
    public ResponseEntity<?> addPayroll(@RequestBody Payroll payroll) {
        try {
            Payroll created = payrollService.addPayroll(payroll); // Call service method to add payroll
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

    // Update existing payroll
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePayroll(@PathVariable int id, @RequestBody Payroll payroll) {
        try {
            if (payroll.getPayrollId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }

            payroll.setPayrollId(id);
            Payroll updated = payrollService.updatePayroll(payroll); // Call service method to update payroll
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

    // Delete payroll by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayroll(@PathVariable int id) {
        try {
            payrollService.deletePayroll(id); // Call service method to delete payroll
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

    // Generate payroll for a specific employee
    @PostMapping("/generate")
    public ResponseEntity<String> generatePayroll(@RequestBody PayrollRequest request) {
        try {
            Employee employee = employeeService.getEmployeeById(request.getEmployeeId());

            // Calculate net pay using the calculateNetPay method from the PayrollService
            BigDecimal netPay = payrollService.calculateNetPay(employee, request.getStartDate(), request.getEndDate());

            // Create the payroll object and save it
            Payroll payroll = new Payroll();
            payroll.setEmployeeId(request.getEmployeeId());
            payroll.setPeriodStart(request.getStartDate());
            payroll.setPeriodEnd(request.getEndDate());
            payroll.setNetPay(netPay);

            payrollService.addPayroll(payroll); // Save the generated payroll

            return ResponseEntity.ok("✅ Payroll generated for employee " + request.getEmployeeId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating payroll: " + e.getMessage());
        }
    }
}
