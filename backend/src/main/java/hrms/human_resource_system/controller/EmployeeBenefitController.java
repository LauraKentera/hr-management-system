package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeBenefitRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeBenefitResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeBenefitMapper;
import main.java.hrms.human_resource_system.model.Benefit;
import main.java.hrms.human_resource_system.model.EmployeeBenefit;
import main.java.hrms.human_resource_system.service.EmployeeBenefitService;
import main.java.hrms.human_resource_system.service.BenefitService;  // Import BenefitService
import main.java.hrms.human_resource_system.service.BenefitItemService;  // Import BenefitItemService
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-benefits")
public class EmployeeBenefitController {

    private final EmployeeBenefitService employeeBenefitService;
    private final BenefitService benefitService;  // Add BenefitService
    private final BenefitItemService benefitItemService;  // Add BenefitItemService

    // Constructor injection
    public EmployeeBenefitController(EmployeeBenefitService employeeBenefitService,
                                     BenefitService benefitService,
                                     BenefitItemService benefitItemService) {
        this.employeeBenefitService = employeeBenefitService;
        this.benefitService = benefitService;  // Initialize BenefitService
        this.benefitItemService = benefitItemService;  // Initialize BenefitItemService
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

        // Retrieve the Benefit object (not BenefitItem)
        Benefit benefit = benefitService.getBenefitById(id);  // This retrieves a Benefit, not a BenefitItem

        if (benefit != null) {
            eb.setBenefit(benefit);  // Set the Benefit object
            employeeBenefitService.updateEmployeeBenefit(id, eb);
            return ResponseEntity.ok("✅ Employee Benefit updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Benefit not found for the provided ID.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeBenefit(@PathVariable int id) {
        employeeBenefitService.deleteEmployeeBenefit(id);
        return ResponseEntity.ok("🗑️ Employee Benefit deleted.");
    }
}
