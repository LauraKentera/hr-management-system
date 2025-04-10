package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Benefit;
import main.java.hrms.human_resource_system.service.BenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benefits")
public class BenefitController {

    private final BenefitService service = new BenefitService();

    @GetMapping
    public ResponseEntity<List<Benefit>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Benefit> getById(@PathVariable int id) {
        Benefit benefit = service.getById(id);
        return benefit != null ? ResponseEntity.ok(benefit) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Benefit benefit) {
        service.insert(benefit);
        return ResponseEntity.ok("✅ Benefit created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Benefit benefit) {
        service.update(id, benefit);
        return ResponseEntity.ok("🔄 Benefit updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Benefit deleted.");
    }
}
