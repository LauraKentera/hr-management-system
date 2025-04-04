package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmployeeChange;
import main.java.hrms.human_resource_system.service.EmployeeChangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-changes")
public class EmployeeChangeController {

    private final EmployeeChangeService service;

    public EmployeeChangeController() {
        this.service = new EmployeeChangeService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeChange> getById(@PathVariable int id) {
        EmployeeChange change = service.getById(id);
        return change != null ? ResponseEntity.ok(change) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EmployeeChange> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeChange change) {
        service.insert(change);
        return ResponseEntity.ok("✅ Change record inserted.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Change record deleted.");
    }
}

