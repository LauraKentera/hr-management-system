package hrms.human_resource_system.service;

import hrms.human_resource_system.model.EmployeeBankAccount;
import hrms.human_resource_system.repository.EmployeeBankAccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeBankAccountService {

    private final EmployeeBankAccountDAO dao;

    @Autowired
    public EmployeeBankAccountService(EmployeeBankAccountDAO dao) {
        this.dao = dao;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public EmployeeBankAccount getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<EmployeeBankAccount> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(EmployeeBankAccount entity) {
        wrap(() -> {
            validateEmployeeBankAccount(entity);
            dao.insert(entity);
            return null;
        });
    }

    public void update(int id, EmployeeBankAccount account) {
        wrap(() -> {
            validateEmployeeBankAccount(account);
            dao.update(account);
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validateEmployeeBankAccount(EmployeeBankAccount entity) {
        if (entity.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Employee ID is required.");
        }
        if (entity.getBankName() == null || entity.getBankName().trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name is required.");
        }
        if (entity.getAccountNumber() == null || entity.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required.");
        }
        if (entity.getAccountNumber().length() < 10 || entity.getAccountNumber().length() > 20) {
            throw new IllegalArgumentException("Account number must be between 10 and 20 characters.");
        }
        if (entity.getIban() != null && entity.getIban().length() != 22) {
            throw new IllegalArgumentException("IBAN must be 22 characters long.");
        }
    }
}
