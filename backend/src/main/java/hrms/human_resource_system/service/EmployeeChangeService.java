package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeChange;
import main.java.hrms.human_resource_system.repository.EmployeeChangeDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.PositionDAO;

import java.util.List;

public class EmployeeChangeService {

    private final EmployeeChangeDAO dao = new EmployeeChangeDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final PositionDAO positionDAO = new PositionDAO();

    // Get EmployeeChange by ID
    public EmployeeChange getById(int id) {
        return dao.getById(id);
    }

    // Get all EmployeeChanges
    public List<EmployeeChange> getAll() {
        return dao.getAll();
    }

    // Insert new EmployeeChange after validation
    public void insert(EmployeeChange entity) {
        validateEmployeeChange(entity);  // Validate before inserting
        dao.insert(entity);
    }

    // Delete an EmployeeChange by ID
    public void delete(int id) {
        dao.delete(id);
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
}
