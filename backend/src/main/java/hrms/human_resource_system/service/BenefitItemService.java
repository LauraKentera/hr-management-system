package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.BenefitItem;
import hrms.human_resource_system.repository.BenefitItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class BenefitItemService {

    private final BenefitItemDAO dao;

    @Autowired
    public BenefitItemService(BenefitItemDAO dao) {
        this.dao = dao;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (DLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e;
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
        return wrap(() -> dao.getByBenefitId(benefitId));
    }

    public BenefitItem create(BenefitItem item, int performedBy) {
        wrap(() -> {
            validate(item);
            dao.insert(item);
            return item;
        });
        return item;
    }

    public BenefitItem update(int id, BenefitItem item, int performedBy) {
        wrap(() -> {
            validate(item);
            dao.update(id, item);
            return item;
        });
        return item;
    }

    public void delete(int id, int performedBy) {
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
    }
}
