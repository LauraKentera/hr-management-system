package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.BenefitItem;
import main.java.hrms.human_resource_system.repository.BenefitItemDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class BenefitItemService {

    private final BenefitItemDAO dao = new BenefitItemDAO();

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

    public BenefitItem getById(int id) {
        return wrap(() -> dao.getById(id));
    }

    public List<BenefitItem> getAll() {
        return wrap(dao::getAll);
    }

    public List<BenefitItem> getByBenefitId(int benefitId) {
        return dao.getByBenefitId(benefitId);
    }    

    public void insert(BenefitItem item) {
        wrap(() -> {
            validate(item);
            dao.insert(item);
            return null;
        });
    }

    public void update(int id, BenefitItem item) {
        wrap(() -> {
            validate(item);
            dao.update(id, item);
            return null;
        });
    }

    public void delete(int id) {
        wrap(() -> {
            dao.delete(id);
            return null;
        });
    }

    private void validate(BenefitItem item) {
        if (item.getFromDate() == null) {
            throw new IllegalArgumentException("From date is required.");
        }
        if (item.getToDate() != null && item.getFromDate().isAfter(item.getToDate())) {
            throw new IllegalArgumentException("From date cannot be after To date.");
        }
        if (item.getBenefitId() <= 0) {
            throw new IllegalArgumentException("Benefit ID must be valid.");
        }
        // Add more validations as needed
    }
}
