package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.DisabilityCategory;
import main.java.hrms.human_resource_system.repository.DisabilityCategoryDAO;

import java.util.List;
import java.util.function.Supplier;

public class DisabilityCategoryService {

    private final DisabilityCategoryDAO dao = new DisabilityCategoryDAO();

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public DisabilityCategory getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<DisabilityCategory> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(DisabilityCategory category) {
        wrap(() -> {
            validateDisabilityCategory(category);
            dao.insert(category);
            return null;
        });
    }

    public void update(int id, DisabilityCategory category) {
        wrap(() -> {
            validateDisabilityCategory(category);
            dao.update(id, category);
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validateDisabilityCategory(DisabilityCategory category) {
        // Validate required fields
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name is required.");
        }

        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Category description is required.");
        }

        if (category.getLegalCode() == null || category.getLegalCode().isEmpty()) {
            throw new IllegalArgumentException("Category legal code is required.");
        }

        // Example additional validation: Check if the name is unique (this can be done in the DAO)
        // Optional: if (dao.existsByName(category.getName())) {
        //     throw new IllegalArgumentException("Category name must be unique.");
        // }
    }
}
