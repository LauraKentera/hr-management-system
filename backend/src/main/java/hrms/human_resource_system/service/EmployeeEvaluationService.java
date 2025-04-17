package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.repository.EmployeeEvaluationDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeEvaluationService {

    private final EmployeeEvaluationDAO dao;
    private final EmployeeDAO employeeDAO;

    // Constructor injection of DAO
    public EmployeeEvaluationService(EmployeeEvaluationDAO dao, EmployeeDAO employeeDAO) {
        this.dao = dao;
        this.employeeDAO = employeeDAO;
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

    // Get EmployeeEvaluation by ID
    public EmployeeEvaluation getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    // Get all EmployeeEvaluations
    public List<EmployeeEvaluation> getAll() {
        return wrap(dao::getAll);
    }

    // Insert new EmployeeEvaluation after validation
    public void insert(EmployeeEvaluation entity, int performedBy) {
        wrap(() -> {
            validateEmployeeEvaluation(entity);  // Validate the entity before insertion
            dao.insert(entity, performedBy);  // Pass performedBy to the DAO method
            return null;
        });
    }

    // Update existing EmployeeEvaluation after validation
    public void update(int id, EmployeeEvaluation entity, int performedBy) {
        wrap(() -> {
            validateEmployeeEvaluation(entity);  // Validate the entity before updating
            dao.update(id, entity, performedBy);  // Pass performedBy to the DAO method
            return null;
        });
    }

    // Delete EmployeeEvaluation by ID
    public void delete(int id, int performedBy) {
        wrap(() -> {
            dao.delete(id, performedBy);  // Pass performedBy to the DAO method for logging
            return null;
        });
    }

    // Validation for Employee Evaluation
    private void validateEmployeeEvaluation(EmployeeEvaluation evaluation) {
        // Ensure evaluation date is provided
        if (evaluation.getEvaluationDate() == null) {
            throw new IllegalArgumentException("Evaluation date is required.");
        }

        // Ensure score is within a valid range (example: 0-100)
        if (evaluation.getScore() == null || evaluation.getScore() < 0 || evaluation.getScore() > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }

        // Ensure the userId exists in the database
        if (!employeeDAO.existsById(evaluation.getUserId())) {
            throw new IllegalArgumentException("Employee with ID " + evaluation.getUserId() + " does not exist.");
        }

        // Optional: Add more validation checks here as needed
    }
}
