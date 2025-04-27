package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Benefit;
import hrms.human_resource_system.repository.BenefitDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class BenefitService {

    private final BenefitDAO dao = new BenefitDAO();

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (DLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public Benefit getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<Benefit> getAll() {
        return wrap(dao::getAll);
    }

    // Updated to handle performedBy argument
    public Benefit create(Benefit benefit, int performedBy) {
        wrap(() -> {
            validate(benefit);
            dao.insert(benefit, performedBy); // Passing performedBy for audit logging
            return benefit;
        });
        return benefit;
    }

    // Updated to handle performedBy argument
    public Benefit update(int id, Benefit benefit, int performedBy) {
        wrap(() -> {
            validate(benefit);
            dao.update(id, benefit, performedBy); // Passing performedBy for audit logging
            return benefit;
        });
        return benefit;
    }

    public void delete(int id, int performedBy) {
        wrap(() -> {
            dao.delete(id, performedBy);
            return null;
        });
    }

    private void validate(Benefit benefit) {
        if (benefit.getName() == null || benefit.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Benefit name is required.");
        }

        if (benefit.isTaxable() == null) {
            throw new IllegalArgumentException("Taxable status is required.");
        }

        if (benefit.isActive() == null) {
            throw new IllegalArgumentException("Benefit active status is required.");
        }

        // Add more business rule validation as needed
    }
}
