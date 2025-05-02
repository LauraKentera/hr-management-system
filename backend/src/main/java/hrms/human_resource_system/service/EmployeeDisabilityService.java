package hrms.human_resource_system.service;

import hrms.human_resource_system.model.EmployeeDisability;
import hrms.human_resource_system.repository.DisabilityCategoryDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.EmployeeDisabilityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeDisabilityService {

    private final EmployeeDisabilityDAO dao;
    private final EmployeeDAO employeeDAO;
    private final DisabilityCategoryDAO disabilityCategoryDAO;

    @Autowired
    public EmployeeDisabilityService(EmployeeDisabilityDAO dao,
                                     EmployeeDAO employeeDAO,
                                     DisabilityCategoryDAO disabilityCategoryDAO) {
        this.dao = dao;
        this.employeeDAO = employeeDAO;
        this.disabilityCategoryDAO = disabilityCategoryDAO;
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

    public EmployeeDisability getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<EmployeeDisability> getAll() {
        return wrap(dao::getAll);
    }

    public EmployeeDisability insert(EmployeeDisability entity) {
        wrap(() -> {
            validateEmployeeDisability(entity);
            dao.insert(entity);
            return null;
        });
        return entity;
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    public EmployeeDisability update(EmployeeDisability disability) {
        return wrap(() -> {
            validateEmployeeDisability(disability);
            EmployeeDisability existingDisability = dao.getById(disability.getEmployeeDisabilityId());
            if (existingDisability == null) {
                throw new IllegalArgumentException("Employee disability record not found with ID: " + disability.getEmployeeDisabilityId());
            }
            dao.update(disability.getEmployeeDisabilityId(), disability);
            return disability;
        });
    }

    private void validateEmployeeDisability(EmployeeDisability entity) {
        if (!employeeDAO.existsById(entity.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + entity.getEmployeeId() + " does not exist.");
        }

        if (!disabilityCategoryDAO.existsById(entity.getDisabilityCategoryId())) {
            throw new IllegalArgumentException("Disability category with ID " + entity.getDisabilityCategoryId() + " does not exist.");
        }

        if (entity.getFromDate().isAfter(entity.getToDate())) {
            throw new IllegalArgumentException("Start date cannot be after the end date.");
        }

        if (entity.getPercentage() < 0 || entity.getPercentage() > 100) {
            throw new IllegalArgumentException("Disability percentage must be between 0 and 100.");
        }
    }
}
