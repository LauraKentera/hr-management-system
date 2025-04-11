package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Nationality;
import main.java.hrms.human_resource_system.repository.NationalityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class NationalityService {

    private final NationalityDAO nationalityDAO;

    @Autowired
    public NationalityService(NationalityDAO nationalityDAO) {
        this.nationalityDAO = nationalityDAO;
    }

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

    // Get all nationalities
    public List<Nationality> getAllNationalities() {
        return wrap(nationalityDAO::getAll);
    }

    // Get nationality by ID
    public Nationality getNationalityById(Integer nationalityId) {
        if (nationalityId == null) {
            throw new IllegalArgumentException("Nationality ID cannot be null");
        }
        return wrap(() -> nationalityDAO.getById(nationalityId));
    }

    // Add new nationality
    public void addNationality(Nationality nationality) {
        wrap(() -> {
            validateNationality(nationality);
            nationalityDAO.insert(nationality);
            return null;  // return type is Void
        });
    }

    // Update existing nationality
    public void updateNationality(Nationality nationality) {
        wrap(() -> {
            validateNationality(nationality);
            nationalityDAO.update(nationality);
            return null;  // return type is Void
        });
    }

    // Delete nationality by ID
    public void deleteNationality(Integer nationalityId) {
        wrap(() -> {
            if (nationalityId == null) {
                throw new IllegalArgumentException("Nationality ID cannot be null");
            }
            nationalityDAO.delete(nationalityId);
            return null;  // return type is Void
        });
    }

    // Validation for nationality data
    private void validateNationality(Nationality nationality) {
        if (nationality.getName() == null || nationality.getName().isEmpty()) {
            throw new IllegalArgumentException("Nationality name cannot be null or empty");
        }

        if (nationality.getUserId() == null || nationality.getUserId() <= 0) {
            throw new IllegalArgumentException("User ID must be valid");
        }

        if (nationality.getIsActive() == null) {
            throw new IllegalArgumentException("IsActive flag must be specified");
        }
    }
}
