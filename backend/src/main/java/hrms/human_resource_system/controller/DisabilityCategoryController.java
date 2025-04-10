package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.DisabilityCategory;
import main.java.hrms.human_resource_system.service.DisabilityCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disability-categories")
public class DisabilityCategoryController {

    private final DisabilityCategoryService service = new DisabilityCategoryService();

    @GetMapping
    public ResponseEntity<List<DisabilityCategory>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisabilityCategory> getById(@PathVariable int id) {
        DisabilityCategory category = service.getById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody DisabilityCategory category) {
        service.insert(category);
        return ResponseEntity.ok("✅ Disability category created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody DisabilityCategory category) {
        service.update(id, category);
        return ResponseEntity.ok("🔄 Disability category updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Disability category deleted.");
    }
}
