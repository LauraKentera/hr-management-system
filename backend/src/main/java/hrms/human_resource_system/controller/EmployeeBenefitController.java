package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmployeeBenefit;
import main.java.hrms.human_resource_system.service.EmployeeBenefitService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeeBenefits")
public class EmployeeBenefitController {

    private final EmployeeBenefitService employeeBenefitService;

    // Constructor Injection of EmployeeBenefitService
    public EmployeeBenefitController(EmployeeBenefitService employeeBenefitService) {
        this.employeeBenefitService = employeeBenefitService;
    }

    // GET all employee benefits
    @GetMapping
    public ResponseEntity<List<EmployeeBenefit>> getAllEmployeeBenefits() {
        List<EmployeeBenefit> employeeBenefits = employeeBenefitService.getAllEmployeeBenefits();
        return ResponseEntity.ok(employeeBenefits);
    }

    // GET employee benefit by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeBenefit> getEmployeeBenefitById(@PathVariable int id) {
        EmployeeBenefit employeeBenefit = employeeBenefitService.getEmployeeBenefitById(id);
        if (employeeBenefit != null) {
            return ResponseEntity.ok(employeeBenefit);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // POST new employee benefit
    @PostMapping
    public ResponseEntity<String> createEmployeeBenefit(@RequestBody EmployeeBenefit employeeBenefit) {
        employeeBenefitService.addEmployeeBenefit(employeeBenefit);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Employee Benefit created.");
    }

    // PUT update an existing employee benefit
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeBenefit(@PathVariable int id, @RequestBody EmployeeBenefit employeeBenefit) {
        employeeBenefitService.updateEmployeeBenefit(id, employeeBenefit);
        return ResponseEntity.ok("✅ Employee Benefit updated.");
    }

    // DELETE employee benefit by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeBenefit(@PathVariable int id) {
        employeeBenefitService.deleteEmployeeBenefit(id);
        return ResponseEntity.ok("🗑️ Employee Benefit deleted.");
    }
}
