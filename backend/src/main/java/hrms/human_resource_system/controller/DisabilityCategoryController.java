package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.DisabilityCategoryResponseDTO;
import main.java.hrms.human_resource_system.mapper.DisabilityCategoryMapper;
import main.java.hrms.human_resource_system.model.DisabilityCategory;
import main.java.hrms.human_resource_system.service.DisabilityCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disability-categories")
public class DisabilityCategoryController {

    private final DisabilityCategoryService service;

    public DisabilityCategoryController(DisabilityCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DisabilityCategoryResponseDTO>> getAll() {
        List<DisabilityCategoryResponseDTO> dtoList = service.getAll().stream()
                .map(DisabilityCategoryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisabilityCategoryResponseDTO> getById(@PathVariable int id) {
        DisabilityCategory category = service.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(DisabilityCategoryMapper.toDTO(category));
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
