package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.repository.EmployeeAbsenceDAO;

import java.util.List;
import java.util.function.Supplier;

public class EmployeeAbsenceService {

    private final EmployeeAbsenceDAO dao = new EmployeeAbsenceDAO();

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

    public EmployeeAbsence getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<EmployeeAbsence> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(EmployeeAbsence entity) {
        wrap(() -> {
            validateEmployeeAbsence(entity);  // Validate before inserting
            dao.insert(entity);
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    // Validation method for EmployeeAbsence
    private void validateEmployeeAbsence(EmployeeAbsence entity) {
        // Validate required fields
        if (entity.getAbsenceTypeId() <= 0) {
            throw new IllegalArgumentException("Absence Type ID is required.");
        }
        if (entity.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required.");
        }
        if (entity.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required.");
        }

        // Ensure logical condition: start date should be before end date
        if (entity.getStartDate().isAfter(entity.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
    }
}
