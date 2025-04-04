package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmployeeBankAccount;
import main.java.hrms.human_resource_system.service.EmployeeBankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-bank-accounts")
public class EmployeeBankAccountController {

    private final EmployeeBankAccountService service;

    public EmployeeBankAccountController() {
        this.service = new EmployeeBankAccountService();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeBankAccount> getById(@PathVariable int employeeId) {
        EmployeeBankAccount account = service.getById(employeeId);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<EmployeeBankAccount> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeBankAccount account) {
        service.insert(account);
        return ResponseEntity.ok("✅ Employee bank account saved.");
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> delete(@PathVariable int employeeId) {
        service.delete(employeeId);
        return ResponseEntity.ok("🗑️ Bank account deleted for employee ID " + employeeId);
    }
}

