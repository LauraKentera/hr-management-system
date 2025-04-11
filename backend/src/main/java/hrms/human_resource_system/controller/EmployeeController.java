package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeCreateRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeUpdateRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeMapper;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.service.EmployeeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> dtos = employeeService.getAllEmployees().stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return employee != null
                ? ResponseEntity.ok(EmployeeMapper.toDTO(employee))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCreateRequestDTO dto) {
        try {
            Employee employee = EmployeeMapper.toEntity(dto);
            employeeService.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Employee created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @RequestBody EmployeeUpdateRequestDTO dto) {
        try {
            Employee employee = EmployeeMapper.toEntity(dto);
            employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok("✅ Employee updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("🗑️ Employee deleted.");
    }
}
