package main.java.hrms.human_resource_system.controller;

import  main.java.hrms.human_resource_system.model.EmployeeDisability;
import  main.java.hrms.human_resource_system.service.EmployeeDisabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-disabilities")
public class EmployeeDisabilityController {

    private final EmployeeDisabilityService service;

    public EmployeeDisabilityController() {
        this.service = new EmployeeDisabilityService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDisability> getById(@PathVariable int id) {
        EmployeeDisability record = service.getById(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EmployeeDisability> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeDisability record) {
        service.insert(record);
        return ResponseEntity.ok("✅ Disability record created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Disability record deleted.");
    }
}
