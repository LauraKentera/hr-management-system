package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeChange;
import main.java.hrms.human_resource_system.repository.EmployeeChangeDAO;

import java.util.List;

public class EmployeeChangeService {

    private final EmployeeChangeDAO dao = new EmployeeChangeDAO();

    public EmployeeChange getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeChange> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeChange entity) {
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}

