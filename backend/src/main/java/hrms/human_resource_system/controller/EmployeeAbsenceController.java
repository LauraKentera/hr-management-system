package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.service.EmployeeAbsenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-absences")
public class EmployeeAbsenceController {

    private final EmployeeAbsenceService service;

    public EmployeeAbsenceController() {
        this.service = new EmployeeAbsenceService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAbsence> getById(@PathVariable int id) {
        EmployeeAbsence absence = service.getById(id);
        return absence != null ? ResponseEntity.ok(absence) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EmployeeAbsence> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeAbsence absence) {
        service.insert(absence);
        return ResponseEntity.ok("✅ Absence created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Absence deleted.");
    }
}
