package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmployeeAbsence;
import hrms.human_resource_system.repository.EmployeeAbsenceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeAbsenceService {

    private final EmployeeAbsenceDAO dao;

    @Autowired
    public EmployeeAbsenceService(EmployeeAbsenceDAO dao) {
        this.dao = dao;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException | DLException e) {
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

    public EmployeeAbsence update(int id, EmployeeAbsence absence) {
        return wrap(() -> {
            validateEmployeeAbsence(absence);
            dao.update(id, absence);
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

    public void approveAbsence(int absenceId, String status, int approvedBy) {
        EmployeeAbsence absence = dao.getById(absenceId);
        if (absence != null) {
            absence.setStatus(status);  // 'Approved' or 'Rejected'
            absence.setApprovedBy(approvedBy); // The user who approves
            dao.update(absenceId, absence);  // Update the absence status in the DB
        } else {
            throw new IllegalArgumentException("Absence not found.");
        }
    }

    public void approveOrDenyAbsence(int absenceId, String status, int approvedBy) {
        wrap(() -> {
            EmployeeAbsence absence = dao.getById(absenceId);
            if (absence == null) {
                throw new IllegalArgumentException("Absence not found with ID: " + absenceId);
            }
            
            if (!"Pending".equals(absence.getStatus())) {
                throw new IllegalArgumentException("Only pending absences can be modified");
            }
            
            if (!"Approved".equals(status) && !"Rejected".equals(status)) {
                throw new IllegalArgumentException("Invalid status. Must be 'Approved' or 'Rejected'");
            }
            
            absence.setStatus(status);
            absence.setApprovedBy(approvedBy);
            dao.update(absenceId, absence);
            return null;
        });
    }

}
