package main.java.hrms.human_resource_system.controller;

import  main.java.hrms.human_resource_system.model.BenefitItem;
import  main.java.hrms.human_resource_system.service.BenefitItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benefit-items")
public class BenefitItemController {

    private final BenefitItemService service;

    public BenefitItemController() {
        this.service = new BenefitItemService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BenefitItem> getById(@PathVariable int id) {
        BenefitItem item = service.getById(id);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<BenefitItem> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody BenefitItem item) {
        service.insert(item);
        return ResponseEntity.ok("✅ Benefit item created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Benefit item deleted.");
    }
}

