package hrms.human_resource_system.service;

import hrms.human_resource_system.model.EmployeeChange;
import hrms.human_resource_system.repository.EmployeeChangeDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.PositionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeChangeService {

    private final EmployeeChangeDAO dao;
    private final EmployeeDAO employeeDAO;
    private final PositionDAO positionDAO;

    @Autowired
    public EmployeeChangeService(EmployeeChangeDAO dao,
                                 EmployeeDAO employeeDAO,
                                 PositionDAO positionDAO) {
        this.dao = dao;
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
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

    public EmployeeChange getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<EmployeeChange> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(EmployeeChange entity) {
        wrap(() -> {
            validateEmployeeChange(entity);
            dao.insert(entity);
            return null;
        });
    }

    public EmployeeChange create(EmployeeChange entity) {
        return wrap(() -> {
            validateEmployeeChange(entity);
            dao.insert(entity);
            return entity;
        });
    }

    public EmployeeChange update(EmployeeChange entity) {
        return wrap(() -> {
            validateEmployeeChange(entity);
            dao.update(entity);
            return entity;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validateEmployeeChange(EmployeeChange entity) {
        if (!employeeDAO.existsById(entity.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + entity.getEmployeeId() + " does not exist.");
        }

        if (!positionDAO.existsById(entity.getNewPositionId())) {
            throw new IllegalArgumentException("New position with ID " + entity.getNewPositionId() + " does not exist.");
        }

        if (entity.getOldSalary() != null && entity.getOldSalary().compareTo(entity.getNewSalary()) < 0) {
            throw new IllegalArgumentException("Old salary cannot be less than the new salary.");
        }

        if (entity.getChangeDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Change date cannot be in the future.");
        }
    }
}
