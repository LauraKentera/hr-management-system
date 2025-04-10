package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.repository.EmployeeEvaluationDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeEvaluationService {

    private final EmployeeEvaluationDAO dao;
    private final EmployeeDAO employeeDAO;

    // Constructor injection of DAO
    public EmployeeEvaluationService(EmployeeEvaluationDAO dao, EmployeeDAO employeeDAO) {
        this.dao = dao;
        this.employeeDAO = employeeDAO;
    }

    public EmployeeEvaluation getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeEvaluation> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeEvaluation entity) {
        validateEmployeeEvaluation(entity);
        dao.insert(entity);
    }

    public void update(int id, EmployeeEvaluation entity) {
        validateEmployeeEvaluation(entity);
        dao.update(id, entity);
    }

    public void delete(int id) {
        dao.delete(id);
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
