package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.repository.DepartmentDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;

import java.util.List;

public class DepartmentService {

    private final DepartmentDAO dao = new DepartmentDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO(); // Add EmployeeDAO to check if the manager exists

    public Department getById(int id) {
        return dao.getById(id);
    }

    public List<Department> getAll() {
        return dao.getAll();
    }

    public void insert(Department entity) {
        // Validate before inserting
        validateDepartment(entity);
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    private void validateDepartment(Department department) {
        // Validate required fields
        if (department.getName() == null || department.getName().isEmpty()) {
            throw new IllegalArgumentException("Department name is required.");
        }

        // Check if manager exists
        if (department.getManagerId() != null && !employeeDAO.existsById(department.getManagerId())) {
            throw new IllegalArgumentException("Manager with ID " + department.getManagerId() + " does not exist.");
        }
    }
}
