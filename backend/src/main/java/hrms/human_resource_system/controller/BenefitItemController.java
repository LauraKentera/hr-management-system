package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.BenefitItemRequestDTO;
import main.java.hrms.human_resource_system.dto.BenefitItemResponseDTO;
import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.mapper.BenefitItemMapper;
import main.java.hrms.human_resource_system.model.BenefitItem;
import main.java.hrms.human_resource_system.service.BenefitItemService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/benefit-items")
public class BenefitItemController {

    private final BenefitItemService service;

    public BenefitItemController(BenefitItemService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BenefitItemResponseDTO> getById(@PathVariable int id) {
        BenefitItem item = service.getById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BenefitItemMapper.toDTO(item));
    }

    @GetMapping
    public ResponseEntity<List<BenefitItemResponseDTO>> getAll() {
        List<BenefitItemResponseDTO> dtoList = service.getAll().stream()
                .map(BenefitItemMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/benefit/{id}")
    public ResponseEntity<List<BenefitItemResponseDTO>> getByBenefitId(@PathVariable int id) {
        List<BenefitItemResponseDTO> dtoList = service.getByBenefitId(id).stream()
                .map(BenefitItemMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BenefitItemRequestDTO dto) {
        try {
            BenefitItem item = BenefitItemMapper.toEntity(dto);
            service.insert(item);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Benefit item created.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CustomErrorResponse(ex.getMessage(), 400));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Benefit item deleted.");
    }
}
