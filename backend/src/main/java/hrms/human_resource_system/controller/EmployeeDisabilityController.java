package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeDisabilityRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeDisabilityResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeDisabilityMapper;
import main.java.hrms.human_resource_system.model.EmployeeDisability;
import main.java.hrms.human_resource_system.service.EmployeeDisabilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-disabilities")
public class EmployeeDisabilityController {

    private final EmployeeDisabilityService service;

    public EmployeeDisabilityController(EmployeeDisabilityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDisabilityResponseDTO> getById(@PathVariable int id) {
        EmployeeDisability record = service.getById(id);
        if (record != null) {
            return ResponseEntity.ok(EmployeeDisabilityMapper.toDTO(record));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDisabilityResponseDTO>> getAll() {
        List<EmployeeDisabilityResponseDTO> dtos = service.getAll().stream()
                .map(EmployeeDisabilityMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeDisabilityRequestDTO dto) {
        EmployeeDisability record = EmployeeDisabilityMapper.toEntity(dto);
        service.insert(record);
        return ResponseEntity.ok("✅ Disability record created.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.ok("🗑️ Disability record deleted.");
    }
}
