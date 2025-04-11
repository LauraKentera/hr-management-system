package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EducationLevel;
import main.java.hrms.human_resource_system.repository.EducationLevelDAO;

import java.util.List;
import java.util.function.Supplier;

public class EducationLevelService {

    private final EducationLevelDAO dao = new EducationLevelDAO();

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

    public EducationLevel getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<EducationLevel> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(EducationLevel educationLevel) {
        wrap(() -> {
            validateEducationLevel(educationLevel);
            dao.insert(educationLevel);
            return null;
        });
    }

    public void update(int id, EducationLevel educationLevel) {
        wrap(() -> {
            validateEducationLevel(educationLevel);
            dao.update(id, educationLevel);
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validateEducationLevel(EducationLevel educationLevel) {
        // Validate required fields
        if (educationLevel.getName() == null || educationLevel.getName().isEmpty()) {
            throw new IllegalArgumentException("Education Level name is required.");
        }

        // Example additional validation (optional): Check if the name is unique (this can be done in the DAO)
        // if (dao.existsByName(educationLevel.getName())) {
        //     throw new IllegalArgumentException("Education Level name must be unique.");
        // }
    }
}
