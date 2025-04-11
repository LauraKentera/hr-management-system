package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeChangeRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeChangeResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeChangeMapper;
import main.java.hrms.human_resource_system.model.EmployeeChange;
import main.java.hrms.human_resource_system.service.EmployeeChangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-changes")
public class EmployeeChangeController {

    private final EmployeeChangeService service;

    public EmployeeChangeController(EmployeeChangeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeChangeResponseDTO> getById(@PathVariable int id) {
        EmployeeChange change = service.getById(id);
        if (change != null) {
            return ResponseEntity.ok(EmployeeChangeMapper.toDTO(change));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployeeChangeResponseDTO>> getAll() {
        List<EmployeeChangeResponseDTO> changes = service.getAll().stream()
                .map(EmployeeChangeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(changes);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeChangeRequestDTO dto) {
        EmployeeChange change = EmployeeChangeMapper.toEntity(dto);
        service.insert(change);
        return ResponseEntity.ok("✅ Change record inserted.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Change record deleted.");
    }
}
