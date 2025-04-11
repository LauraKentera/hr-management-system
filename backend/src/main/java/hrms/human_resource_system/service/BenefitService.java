package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Benefit;
import main.java.hrms.human_resource_system.repository.BenefitDAO;

import java.util.List;
import java.util.function.Supplier;

public class BenefitService {

    private final BenefitDAO dao = new BenefitDAO();

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (DLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public Benefit getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<Benefit> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(Benefit benefit) {
        wrap(() -> {
            validate(benefit);
            dao.insert(benefit);
            return null;
        });
    }

    public void update(int id, Benefit benefit) {
        wrap(() -> {
            validate(benefit);
            dao.update(id, benefit);
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validate(Benefit benefit) {
        if (benefit.getName() == null || benefit.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Benefit name is required.");
        }

        if (benefit.isTaxable() == null) {
            throw new IllegalArgumentException("Taxable status is required.");
        }

        if (benefit.isActive() == null) {
            throw new IllegalArgumentException("Benefit active status is required.");
        }

        // Add more business rule validation as needed
    }
}
