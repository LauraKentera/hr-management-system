package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.BenefitItem;
import main.java.hrms.human_resource_system.repository.BenefitItemDAO;

import java.util.List;

public class BenefitItemService {

    private final BenefitItemDAO dao = new BenefitItemDAO();

    public BenefitItem getById(int id) {
        return dao.getById(id);
    }

    public List<BenefitItem> getAll() {
        return dao.getAll();
    }

    public void insert(BenefitItem entity) {
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}

