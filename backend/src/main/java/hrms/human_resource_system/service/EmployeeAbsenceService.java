package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.repository.EmployeeAbsenceDAO;

import java.util.List;

public class EmployeeAbsenceService {

    private final EmployeeAbsenceDAO dao = new EmployeeAbsenceDAO();

    public EmployeeAbsence getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeAbsence> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeAbsence entity) {
        // Validate before inserting
        validateEmployeeAbsence(entity);
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
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
