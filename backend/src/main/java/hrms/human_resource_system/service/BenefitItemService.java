package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.BenefitItem;
import main.java.hrms.human_resource_system.repository.BenefitItemDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitItemService {

    private final BenefitItemDAO dao = new BenefitItemDAO();

    public BenefitItem getById(int id) {
        return dao.getById(id);
    }

    public List<BenefitItem> getAll() {
        return dao.getAll();
    }

    public List<BenefitItem> getByBenefitId(int benefitId) {
        return dao.getByBenefitId(benefitId);
    }    

    public void insert(BenefitItem item) {
        validate(item);
        dao.insert(item);
    }

    public void update(int id, BenefitItem item) {
        validate(item);
        dao.update(id, item);
    }

    public void delete(int id) {
        dao.delete(id);
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


