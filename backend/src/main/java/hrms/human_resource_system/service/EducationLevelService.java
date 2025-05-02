package hrms.human_resource_system.service;

import hrms.human_resource_system.model.EducationLevel;
import hrms.human_resource_system.repository.EducationLevelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EducationLevelService {

    private final EducationLevelDAO dao;

    @Autowired
    public EducationLevelService(EducationLevelDAO dao) {
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
        if (educationLevel.getName() == null || educationLevel.getName().isBlank()) {
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
