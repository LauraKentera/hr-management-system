package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.service.EmployeeEvaluationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-evaluations")
public class EmployeeEvaluationController {

    private final EmployeeEvaluationService service;

    public EmployeeEvaluationController() {
        this.service = new EmployeeEvaluationService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEvaluation> getById(@PathVariable int id) {
        EmployeeEvaluation eval = service.getById(id);
        return eval != null ? ResponseEntity.ok(eval) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EmployeeEvaluation> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeEvaluation eval) {
        service.insert(eval);
        return ResponseEntity.ok("✅ Evaluation record saved.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Evaluation deleted.");
    }
}

