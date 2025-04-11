package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.BenefitRequestDTO;
import main.java.hrms.human_resource_system.dto.BenefitResponseDTO;
import main.java.hrms.human_resource_system.mapper.BenefitMapper;
import main.java.hrms.human_resource_system.model.Benefit;
import main.java.hrms.human_resource_system.service.BenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/benefits")
public class BenefitController {

    private final BenefitService service;

    public BenefitController(BenefitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BenefitResponseDTO>> getAll() {
        List<Benefit> benefits = service.getAll();
        List<BenefitResponseDTO> dtoList = benefits.stream()
                .map(BenefitMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BenefitResponseDTO> getById(@PathVariable int id) {
        Benefit benefit = service.getById(id);
        if (benefit == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(BenefitMapper.toDTO(benefit));
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody BenefitRequestDTO dto) {
        Benefit benefit = BenefitMapper.toEntity(dto);
        service.insert(benefit);
        return ResponseEntity.ok("✅ Benefit created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody BenefitRequestDTO dto) {
        Benefit updated = BenefitMapper.toEntity(dto);
        service.update(id, updated);
        return ResponseEntity.ok("🔄 Benefit updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Benefit deleted.");
    }
}
