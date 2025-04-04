package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController() {
        this.service = new DepartmentService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable int id) {
        Department dept = service.getById(id);
        return dept != null ? ResponseEntity.ok(dept) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Department> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Department dept) {
        service.insert(dept);
        return ResponseEntity.ok("✅ Department created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Department deleted.");
    }
}

