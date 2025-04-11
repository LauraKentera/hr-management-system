package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeDisability;
import main.java.hrms.human_resource_system.repository.EmployeeDisabilityDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.DisabilityCategoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeDisabilityService {

    private final EmployeeDisabilityDAO dao = new EmployeeDisabilityDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();  // Assuming EmployeeDAO is available
    private final DisabilityCategoryDAO disabilityCategoryDAO = new DisabilityCategoryDAO();  // Assuming DisabilityCategoryDAO is available

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

    // Get EmployeeDisability by ID
    public EmployeeDisability getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    // Get all EmployeeDisabilities
    public List<EmployeeDisability> getAll() {
        return wrap(dao::getAll);
    }

    // Insert new EmployeeDisability after validation
    public void insert(EmployeeDisability entity) {
        wrap(() -> {
            validateEmployeeDisability(entity);  // Validate before inserting
            dao.insert(entity);
            return null;
        });
    }

    // Delete an EmployeeDisability by ID
    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    // Validation for EmployeeDisability
    private void validateEmployeeDisability(EmployeeDisability entity) {
        // Check if employee exists
        if (!employeeDAO.existsById(entity.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + entity.getEmployeeId() + " does not exist.");
        }

        // Check if disability category exists
        if (!disabilityCategoryDAO.existsById(entity.getDisabilityCategoryId())) {
            throw new IllegalArgumentException("Disability category with ID " + entity.getDisabilityCategoryId() + " does not exist.");
        }

        // Logical validation: Check if the start date is before the end date
        if (entity.getFromDate().isAfter(entity.getToDate())) {
            throw new IllegalArgumentException("Start date cannot be after the end date.");
        }

        // Optional: Check if disability percentage is valid (e.g., between 0 and 100)
        if (entity.getPercentage() < 0 || entity.getPercentage() > 100) {
            throw new IllegalArgumentException("Disability percentage must be between 0 and 100.");
        }
    }
}
