package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.service.EmployeeEvaluationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-evaluations")
public class EmployeeEvaluationController {

    private final EmployeeEvaluationService service;

    // Constructor Injection (Spring will handle the service injection)
    public EmployeeEvaluationController(EmployeeEvaluationService service) {
        this.service = service;
    }

    // GET employee evaluation by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEvaluation> getById(@PathVariable int id) {
        EmployeeEvaluation eval = service.getById(id);
        return eval != null ? ResponseEntity.ok(eval) : ResponseEntity.notFound().build();
    }

    // GET all employee evaluations
    @GetMapping
    public ResponseEntity<List<EmployeeEvaluation>> getAll() {
        List<EmployeeEvaluation> evaluations = service.getAll();
        return ResponseEntity.ok(evaluations);
    }

    // POST create a new employee evaluation
    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeEvaluation eval) {
        try {
            service.insert(eval);  // Will trigger validation before insertion
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Evaluation record saved.");
        } catch (IllegalArgumentException e) {
            // Handling validation errors (bad data)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Error: " + e.getMessage());
        }
    }

    // DELETE employee evaluation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Evaluation deleted.");
    }
}
