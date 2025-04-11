package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeBenefitRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeBenefitResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeBenefitMapper;
import main.java.hrms.human_resource_system.model.EmployeeBenefit;
import main.java.hrms.human_resource_system.service.EmployeeBenefitService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-benefits")
public class EmployeeBenefitController {

    private final EmployeeBenefitService employeeBenefitService;

    public EmployeeBenefitController(EmployeeBenefitService employeeBenefitService) {
        this.employeeBenefitService = employeeBenefitService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeBenefitResponseDTO>> getAllEmployeeBenefits() {
        List<EmployeeBenefitResponseDTO> dtos = employeeBenefitService.getAllEmployeeBenefits()
                .stream()
                .map(EmployeeBenefitMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeBenefitResponseDTO> getEmployeeBenefitById(@PathVariable int id) {
        EmployeeBenefit eb = employeeBenefitService.getEmployeeBenefitById(id);
        if (eb != null) {
            return ResponseEntity.ok(EmployeeBenefitMapper.toDTO(eb));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> createEmployeeBenefit(@RequestBody EmployeeBenefitRequestDTO dto) {
        EmployeeBenefit eb = EmployeeBenefitMapper.toEntity(dto);
        employeeBenefitService.addEmployeeBenefit(eb);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Employee Benefit created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeBenefit(@PathVariable int id, @RequestBody EmployeeBenefitRequestDTO dto) {
        EmployeeBenefit eb = EmployeeBenefitMapper.toEntity(dto);
        eb.setBenefit(id);
        employeeBenefitService.updateEmployeeBenefit(id, eb);
        return ResponseEntity.ok("✅ Employee Benefit updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeBenefit(@PathVariable int id) {
        employeeBenefitService.deleteEmployeeBenefit(id);
        return ResponseEntity.ok("🗑️ Employee Benefit deleted.");
    }
}
