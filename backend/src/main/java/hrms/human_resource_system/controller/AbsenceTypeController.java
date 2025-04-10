package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.AbsenceType;
import main.java.hrms.human_resource_system.service.AbsenceTypeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absence-types")
public class AbsenceTypeController {

    private final AbsenceTypeService service = new AbsenceTypeService();

    @GetMapping
    public ResponseEntity<List<AbsenceType>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceType> getById(@PathVariable int id) {
        AbsenceType type = service.getById(id);
        if (type != null) {
            return ResponseEntity.ok(type);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody AbsenceType absenceType) {
        service.insert(absenceType);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Absence type created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody AbsenceType updatedType) {
        updatedType.setAbsenceTypeId(id);
        service.update(updatedType);
        return ResponseEntity.ok("✏️ Absence type updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Absence type deleted.");
    }
}
