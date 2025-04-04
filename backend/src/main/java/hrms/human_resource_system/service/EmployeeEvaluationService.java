package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.repository.EmployeeEvaluationDAO;

import java.util.List;

public class EmployeeEvaluationService {

    private final EmployeeEvaluationDAO dao = new EmployeeEvaluationDAO();

    public EmployeeEvaluation getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeEvaluation> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeEvaluation entity) {
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}
