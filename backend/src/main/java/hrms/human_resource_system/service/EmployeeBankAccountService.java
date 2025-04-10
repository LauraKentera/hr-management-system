package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeBankAccount;
import main.java.hrms.human_resource_system.repository.EmployeeBankAccountDAO;

import java.util.List;

public class EmployeeBankAccountService {

    private final EmployeeBankAccountDAO dao = new EmployeeBankAccountDAO();

    public EmployeeBankAccount getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeBankAccount> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeBankAccount entity) {
        // Validate before inserting
        validateEmployeeBankAccount(entity);
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    // Validation method for EmployeeBankAccount
    private void validateEmployeeBankAccount(EmployeeBankAccount entity) {
        // Validate required fields
        if (entity.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Employee ID is required.");
        }
        if (entity.getBankName() == null || entity.getBankName().trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name is required.");
        }
        if (entity.getAccountNumber() == null || entity.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required.");
        }

        // Optional: Validate account number format, e.g., length, digits, etc.
        if (entity.getAccountNumber().length() < 10 || entity.getAccountNumber().length() > 20) {
            throw new IllegalArgumentException("Account number must be between 10 and 20 characters.");
        }

        // Optional: Validate IBAN if provided
        if (entity.getIban() != null && entity.getIban().length() != 22) {
            throw new IllegalArgumentException("IBAN must be 22 characters long.");
        }
    }
}
