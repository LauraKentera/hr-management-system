package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeEvaluationRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeEvaluationResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeEvaluationMapper;
import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.service.EmployeeEvaluationService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-evaluations")
public class EmployeeEvaluationController {

    private final EmployeeEvaluationService service;

    public EmployeeEvaluationController(EmployeeEvaluationService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEvaluationResponseDTO> getById(@PathVariable int id) {
        EmployeeEvaluation eval = service.getById(id);
        return eval != null
                ? ResponseEntity.ok(EmployeeEvaluationMapper.toDTO(eval))
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEvaluationResponseDTO>> getAll() {
        List<EmployeeEvaluationResponseDTO> dtos = service.getAll().stream()
                .map(EmployeeEvaluationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeEvaluationRequestDTO dto) {
        try {
            EmployeeEvaluation eval = EmployeeEvaluationMapper.toEntity(dto);
            service.insert(eval);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Evaluation record saved.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Evaluation deleted.");
    }
}
