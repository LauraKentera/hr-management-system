package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.repository.DepartmentDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;

import java.util.List;
import java.util.function.Supplier;

public class DepartmentService {

    private final DepartmentDAO dao = new DepartmentDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO(); // Add EmployeeDAO to check if the manager exists

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

    public Department getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<Department> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(Department entity) {
        wrap(() -> {
            validateDepartment(entity);
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
