package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EducationLevel;
import main.java.hrms.human_resource_system.service.EducationLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education-levels")
public class EducationLevelController {

    private final EducationLevelService service = new EducationLevelService();

    @GetMapping
    public ResponseEntity<List<EducationLevel>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationLevel> getById(@PathVariable int id) {
        EducationLevel level = service.getById(id);
        return level != null ? ResponseEntity.ok(level) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody EducationLevel level) {
        service.insert(level);
        return ResponseEntity.ok("✅ Education level created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody EducationLevel level) {
        service.update(id, level);
        return ResponseEntity.ok("🔄 Education level updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Education level deleted.");
    }
}
