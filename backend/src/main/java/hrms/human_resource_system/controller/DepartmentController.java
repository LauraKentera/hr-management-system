package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.DepartmentRequestDTO;
import main.java.hrms.human_resource_system.dto.DepartmentResponseDTO;
import main.java.hrms.human_resource_system.mapper.DepartmentMapper;
import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getById(@PathVariable int id) {
        Department dept = service.getById(id);
        if (dept == null) {
            return ResponseEntity.notFound().build();
        }
        DepartmentResponseDTO dto = DepartmentMapper.toDTO(dept);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAll() {
        List<DepartmentResponseDTO> dtos = service.getAll().stream()
                .map(DepartmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DepartmentRequestDTO dto) {
        Department entity = DepartmentMapper.toEntity(dto);
        service.insert(entity);
        return ResponseEntity.ok("✅ Department created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Department deleted.");
    }
}
