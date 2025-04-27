package hrms.human_resource_system.service;

import hrms.human_resource_system.model.Department;
import hrms.human_resource_system.repository.DepartmentDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class DepartmentService {

    private final DepartmentDAO dao = new DepartmentDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO(); // Check if the manager exists

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

    public void update(Department department) {
        wrap(() -> {
            validateDepartment(department);
            dao.update(department.getDepartmentId(), department, 0); // assuming `0` is the `performedBy`
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id, 0); // assuming `0` is the `performedBy`
            return null;
        });
    }

    private void validateDepartment(Department department) {
        if (department.getName() == null || department.getName().isEmpty()) {
            throw new IllegalArgumentException("Department name is required.");
        }

        // Check if manager exists
        if (department.getManagerId() != null && !employeeDAO.existsById(department.getManagerId())) {
            throw new IllegalArgumentException("Manager with ID " + department.getManagerId() + " does not exist.");
        }
    }
}
