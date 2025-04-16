package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EducationLevel;
import main.java.hrms.human_resource_system.repository.EducationLevelDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
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
        if (educationLevel.getName() == null || educationLevel.getName().isEmpty()) {
            throw new IllegalArgumentException("Education Level name is required.");
        }
        if (educationLevel.getUserId() <= 0) {
            throw new IllegalArgumentException("Valid userId is required.");
        }
        if (educationLevel.getModificationDate() == null) {
            throw new IllegalArgumentException("Modification date is required.");
        }
    }
}
