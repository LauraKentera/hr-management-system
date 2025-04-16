package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.repository.EmployeeAbsenceDAO;

import java.util.List;
import java.util.function.Supplier;

public class EmployeeAbsenceService {

    private final EmployeeAbsenceDAO dao = new EmployeeAbsenceDAO();

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (DLException e) {
            throw e;
        } catch (Exception e) {
            throw new DLException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public List<EmployeeAbsence> getAll() {
        return wrap(dao::getAll);
    }

    public EmployeeAbsence getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public EmployeeAbsence create(EmployeeAbsence absence) {
        return wrap(() -> {
            validateEmployeeAbsence(absence);
            dao.insert(absence);
            return absence;
        });
    }

    public EmployeeAbsence update(EmployeeAbsence absence) {
        return wrap(() -> {
            validateEmployeeAbsence(absence);
            dao.update(absence);
            return absence;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validateEmployeeAbsence(EmployeeAbsence entity) {
        if (entity.getAbsenceTypeId() <= 0) {
            throw new IllegalArgumentException("Absence Type ID is required.");
        }
        if (entity.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required.");
        }
        if (entity.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required.");
        }
        if (entity.getStartDate().isAfter(entity.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
    }
}
