package hrms.human_resource_system.service;

import hrms.human_resource_system.model.Department;
import hrms.human_resource_system.repository.DepartmentDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class DepartmentService {

    private final DepartmentDAO dao;
    private final EmployeeDAO employeeDAO;

    @Autowired
    public DepartmentService(DepartmentDAO dao, EmployeeDAO employeeDAO) {
        this.dao = dao;
        this.employeeDAO = employeeDAO;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
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
            dao.update(department.getDepartmentId(), department, 0); // `0` is a placeholder for `performedBy`
            return null;
        });
    }

    public void delete(int departmentId) {
        if (!dao.existsById(departmentId)) {
            throw new IllegalArgumentException("Department does not exist.");
        }
    
        if (employeeDAO.existsByDepartment(departmentId)) {
            throw new IllegalStateException("Cannot delete department: employees are assigned to it.");
        }
    
        dao.delete(departmentId); // this is soft-delete now
    }
    

    private void validateDepartment(Department department) {
        if (department.getName() == null || department.getName().isBlank()) {
            throw new IllegalArgumentException("Department name is required.");
        }

        if (department.getManagerId() != null && !employeeDAO.existsById(department.getManagerId())) {
            throw new IllegalArgumentException("Manager with ID " + department.getManagerId() + " does not exist.");
        }
    }
}
