package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeBankAccountRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeBankAccountResponseDTO;
import main.java.hrms.human_resource_system.mapper.EmployeeBankAccountMapper;
import main.java.hrms.human_resource_system.model.EmployeeBankAccount;
import main.java.hrms.human_resource_system.service.EmployeeBankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-bank-accounts")
public class EmployeeBankAccountController {

    private final EmployeeBankAccountService service;

    public EmployeeBankAccountController(EmployeeBankAccountService service) {
        this.service = service;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeBankAccountResponseDTO> getById(@PathVariable int employeeId) {
        EmployeeBankAccount account = service.getById(employeeId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EmployeeBankAccountMapper.toDTO(account));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeBankAccountResponseDTO>> getAll() {
        List<EmployeeBankAccountResponseDTO> dtoList = service.getAll().stream()
                .map(EmployeeBankAccountMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeBankAccountRequestDTO dto) {
        EmployeeBankAccount account = EmployeeBankAccountMapper.toEntity(dto);
        service.insert(account);
        return ResponseEntity.ok("✅ Employee bank account saved.");
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> delete(@PathVariable int employeeId) {
        service.delete(employeeId);
        return ResponseEntity.ok("🗑️ Bank account deleted for employee ID " + employeeId);
    }
}
