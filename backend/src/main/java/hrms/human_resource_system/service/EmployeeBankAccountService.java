package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeBankAccount;
import main.java.hrms.human_resource_system.repository.EmployeeBankAccountDAO;

import java.util.List;

public class EmployeeBankAccountService {

    private final EmployeeBankAccountDAO dao = new EmployeeBankAccountDAO();

    public EmployeeBankAccount getById(int id) {
        return dao.getById(id);
    }

    public List<EmployeeBankAccount> getAll() {
        return dao.getAll();
    }

    public void insert(EmployeeBankAccount entity) {
        dao.insert(entity);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}
