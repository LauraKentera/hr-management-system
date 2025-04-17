package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeChange;
import main.java.hrms.human_resource_system.repository.EmployeeChangeDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.PositionDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeChangeService {

    private final EmployeeChangeDAO dao = new EmployeeChangeDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final PositionDAO positionDAO = new PositionDAO();

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

    // Get EmployeeChange by ID
    public EmployeeChange getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    // Get all EmployeeChanges
    public List<EmployeeChange> getAll() {
        return wrap(dao::getAll);
    }

    // Insert new EmployeeChange after validation
    public void insert(EmployeeChange entity) {
        wrap(() -> {
            validateEmployeeChange(entity);  // Validate before inserting
            dao.insert(entity);
            return null;
        });
    }

    // Delete an EmployeeChange by ID
    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    // Validation for EmployeeChange
    private void validateEmployeeChange(EmployeeChange entity) {
        // Check if employee exists
        if (!employeeDAO.existsById(entity.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + entity.getEmployeeId() + " does not exist.");
        }

        // Check if the new position exists
        if (!positionDAO.existsById(entity.getNewPositionId())) {
            throw new IllegalArgumentException("New position with ID " + entity.getNewPositionId() + " does not exist.");
        }

        // Logical validation: Check if the old salary is greater than or equal to the new salary
        if (entity.getOldSalary().compareTo(entity.getNewSalary()) < 0) {
            throw new IllegalArgumentException("Old salary cannot be less than the new salary.");
        }

        // Ensure change date is in the past (for example)
        if (entity.getChangeDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Change date cannot be in the future.");
        }
    }

    // Create a new EmployeeChange
    public EmployeeChange create(EmployeeChange entity) {
        return wrap(() -> {
            validateEmployeeChange(entity);  // Validate before creating
            dao.insert(entity);  // Save to the database
            return entity;  // Return the created entity
        });
    }

    // Update an existing EmployeeChange
    public EmployeeChange update(EmployeeChange entity) {
        return wrap(() -> {
            validateEmployeeChange(entity);  // Validate before updating
            dao.update(entity);  // Update in the database
            return entity;  // Return the updated entity
        });
    }
}
