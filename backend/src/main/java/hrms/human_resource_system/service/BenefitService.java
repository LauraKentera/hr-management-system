package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Benefit;
import main.java.hrms.human_resource_system.repository.BenefitDAO;

import java.util.List;

public class BenefitService {

    private final BenefitDAO dao = new BenefitDAO();

    public Benefit getById(int id) {
        return dao.getById(id);
    }

    public List<Benefit> getAll() {
        return dao.getAll();
    }

    public void insert(Benefit benefit) {
        validate(benefit); // Call the validation before inserting
        dao.insert(benefit);
    }

    public void update(int id, Benefit benefit) {
        validate(benefit); // Call the validation before updating
        dao.update(id, benefit);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    private void validate(Benefit benefit) {
        if (benefit.getName() == null || benefit.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Benefit name is required.");
        }

        if (benefit.isTaxable() == null) {
            throw new IllegalArgumentException("Taxable status is required.");
        }

        // You can add more validation based on business rules, for example:
        if (benefit.isActive() == null) {
            throw new IllegalArgumentException("Benefit active status is required.");
        }

        // Add more business rule validation as needed
    }
}
