package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeDisability;
import main.java.hrms.human_resource_system.repository.EmployeeDisabilityDAO;

import java.util.List;

public class EmployeeDisabilityService {

    private final EmployeeDisabilityDAO dao = new EmployeeDisabilityDAO();

    public EmployeeDisability getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeDisability> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeDisability entity) {
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}

