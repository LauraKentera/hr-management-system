package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.service.EmployeeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    // GET all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // GET employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}/retirement-status")
    public ResponseEntity<?> getRetirementStatus(@PathVariable int id) {
        try {
            boolean isEligible = employeeService.isEligibleForRetirement(id);
            Map<String, Object> response = new HashMap<>();
            response.put("employeeId", id);
            response.put("isEligibleForRetirement", isEligible);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error checking retirement status", 500));
        }
    }

    @GetMapping("/retirement-eligible")
    public ResponseEntity<?> getRetirementEligibleEmployees() {
        try {
            List<Employee> eligibleEmployees = employeeService.getEmployeesEligibleForRetirement();
            return ResponseEntity.ok(eligibleEmployees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving retirement-eligible employees", 500));
        }
    }

    // POST new employee
    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Employee created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        try {
            employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok("✅ Employee updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("🗑️ Employee deleted.");
    }
}
