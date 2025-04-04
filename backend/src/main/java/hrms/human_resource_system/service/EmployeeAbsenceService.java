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
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}

