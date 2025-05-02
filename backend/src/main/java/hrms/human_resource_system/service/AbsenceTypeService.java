package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.AbsenceType;
import hrms.human_resource_system.repository.AbsenceTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class AbsenceTypeService {

    private final AbsenceTypeDAO dao;

    @Autowired
    public AbsenceTypeService(AbsenceTypeDAO dao) {
        this.dao = dao;
    }

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (DLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;  // Let controller handle validation errors
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public AbsenceType getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<AbsenceType> getAll() {
        return wrap(dao::getAll);
    }

    public void insert(AbsenceType absenceType, int performedBy) {
        wrap(() -> {
            if (absenceType.getName() == null || absenceType.getName().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Name is required.");
            }
            if (absenceType.getCode() == null || absenceType.getCode().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Code is required.");
            }
            dao.insert(absenceType, performedBy);
            return null;
        });
    }

    public void update(int id, AbsenceType absenceType, int performedBy) {
        wrap(() -> {
            if (absenceType.getName() == null || absenceType.getName().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Name is required.");
            }
            if (absenceType.getCode() == null || absenceType.getCode().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Code is required.");
            }
            absenceType.setAbsenceTypeId(id);
            dao.update(absenceType, performedBy);
            return null;
        });
    }

    public void delete(int id, int performedBy) {
        wrap(() -> {
            AbsenceType existing = dao.getById(id);
            if (existing == null) {
                throw new IllegalArgumentException("Absence Type with ID " + id + " does not exist.");
            }
            dao.delete(id, performedBy);
            return null;
        });
    }
}
