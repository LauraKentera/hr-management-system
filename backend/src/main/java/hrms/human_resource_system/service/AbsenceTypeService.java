package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.AbsenceType;
import hrms.human_resource_system.repository.AbsenceTypeDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class AbsenceTypeService {

    private final AbsenceTypeDAO dao = new AbsenceTypeDAO();

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
        return wrap(() -> dao.getById(id)); // Only passing id
    }

    public List<AbsenceType> getAll() {
        return wrap(dao::getAll); // No parameters for getAll
    }

    // Insert method now requires performed_by argument
    public void insert(AbsenceType absenceType, int performedBy) {
        wrap(() -> {
            // Validation
            if (absenceType.getName() == null || absenceType.getName().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Name is required.");
            }

            if (absenceType.getCode() == null || absenceType.getCode().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Code is required.");
            }

            // Insert into database
            dao.insert(absenceType, performedBy); // Pass performedBy to DAO
            return null;
        });
    }

    // Update method now requires performed_by argument
    public void update(int id, AbsenceType absenceType, int performedBy) {
        wrap(() -> {
            // Validation
            if (absenceType.getName() == null || absenceType.getName().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Name is required.");
            }

            if (absenceType.getCode() == null || absenceType.getCode().isEmpty()) {
                throw new IllegalArgumentException("Absence Type Code is required.");
            }

            absenceType.setAbsenceTypeId(id); // Set the ID
            dao.update(absenceType, performedBy); // Pass performedBy to DAO
            return null;
        });
    }

    // Delete method now requires performed_by argument
    public void delete(int id, int performedBy) {
        wrap(() -> {
            AbsenceType existing = dao.getById(id);
            if (existing == null) {
                throw new IllegalArgumentException("Absence Type with ID " + id + " does not exist.");
            }

            dao.delete(id, performedBy); // Pass performedBy to DAO
            return null;
        });
    }
}
