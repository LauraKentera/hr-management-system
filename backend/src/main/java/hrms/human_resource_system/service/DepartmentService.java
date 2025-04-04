package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.repository.DepartmentDAO;

import java.util.List;

public class DepartmentService {

    private final DepartmentDAO dao = new DepartmentDAO();

    public Department getById(int id) {
        return dao.getById(id);
    }

    public List<Department> getAll() {
        return dao.getAll();
    }

    public void insert(Department entity) {
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}

