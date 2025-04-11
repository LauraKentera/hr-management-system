package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.DisabilityCategory;
import main.java.hrms.human_resource_system.repository.DisabilityCategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisabilityCategoryService {

    private final DisabilityCategoryDAO dao = new DisabilityCategoryDAO();

    public DisabilityCategory getById(int id) {
        return dao.getById(id);
    }

    public List<DisabilityCategory> getAll() {
        return dao.getAll();
    }

    public void insert(DisabilityCategory category) {
        // Validate before inserting
        validateDisabilityCategory(category);
        dao.insert(category);
    }

    public void update(int id, DisabilityCategory category) {
        // Validate before updating
        validateDisabilityCategory(category);
        dao.update(id, category);
    }

    public void delete(int id) {
        dao.delete(id);
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
        // This is optional and depends on your business logic
        // if (dao.existsByName(category.getName())) {
        //     throw new IllegalArgumentException("Category name must be unique.");
        // }
    }
}
