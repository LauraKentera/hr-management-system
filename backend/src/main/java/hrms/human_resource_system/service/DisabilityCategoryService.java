package hrms.human_resource_system.service;

import hrms.human_resource_system.model.DisabilityCategory;
import hrms.human_resource_system.repository.DisabilityCategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class DisabilityCategoryService {

    private final DisabilityCategoryDAO dao;

    @Autowired
    public DisabilityCategoryService(DisabilityCategoryDAO dao) {
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
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Category name is required.");
        }
        if (category.getDescription() == null || category.getDescription().isBlank()) {
            throw new IllegalArgumentException("Category description is required.");
        }
        if (category.getLegalCode() == null || category.getLegalCode().isBlank()) {
            throw new IllegalArgumentException("Category legal code is required.");
        }
    }
}
