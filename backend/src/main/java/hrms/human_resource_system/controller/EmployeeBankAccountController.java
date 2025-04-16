package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.EmployeeBankAccount;
import main.java.hrms.human_resource_system.service.EmployeeBankAccountService;
import main.java.hrms.human_resource_system.dto.EmployeeBankAccountRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeBankAccountResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-bank-accounts")
public class EmployeeBankAccountController {

    private final EmployeeBankAccountService service;

    // Constructor injection
    public EmployeeBankAccountController(EmployeeBankAccountService service) {
        this.service = service;
    }

    // Get all employee bank accounts
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<EmployeeBankAccount> bankAccounts = service.getAll();
            List<EmployeeBankAccountResponseDTO> response = bankAccounts.stream()
                    .map(account -> new EmployeeBankAccountResponseDTO(
                            account.getEmployeeId(),
                            account.getBankName(),
                            account.getAccountNumber(),
                            account.getIban()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving bank accounts", 500));
        }
    }

    // Get a single employee bank account by employee ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getById(@PathVariable int employeeId) {
        try {
            EmployeeBankAccount account = service.getById(employeeId);
            return account != null
                    ? ResponseEntity.ok(new EmployeeBankAccountResponseDTO(
                    account.getEmployeeId(),
                    account.getBankName(),
                    account.getAccountNumber(),
                    account.getIban()
            ))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Employee bank account not found with employee ID: " + employeeId, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving bank account", 500));
        }
    }

    // Create a new employee bank account
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeBankAccountRequestDTO requestDTO) {
        try {
            EmployeeBankAccount newAccount = new EmployeeBankAccount(
                    requestDTO.getEmployeeId(),
                    requestDTO.getBankName(),
                    requestDTO.getAccountNumber(),
                    requestDTO.getIban()
            );
            service.insert(newAccount);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating bank account", 500));
        }
    }

    // Update an existing employee bank account
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> update(@PathVariable int employeeId, @RequestBody EmployeeBankAccountRequestDTO requestDTO) {
        try {
            EmployeeBankAccount updatedAccount = new EmployeeBankAccount(
                    employeeId,
                    requestDTO.getBankName(),
                    requestDTO.getAccountNumber(),
                    requestDTO.getIban()
            );
            service.update(employeeId, updatedAccount);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating bank account", 500));
        }
    }

    // Delete an employee bank account by employee ID
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> delete(@PathVariable int employeeId) {
        try {
            service.delete(employeeId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(e.getMessage(), 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error deleting bank account", 500));
        }
    }
}

