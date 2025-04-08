package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmploymentContract;
import main.java.hrms.human_resource_system.repository.ContractDAO;

import java.util.List;

public class ContractService {

    private final ContractDAO contractDAO;

    public ContractService() {
        this.contractDAO = new ContractDAO();
    }

    public List<EmploymentContract> getAllContracts() {
        return contractDAO.getAll();
    }

    public EmploymentContract getContractById(int contractId) {
        return contractDAO.getAll().stream()
                .filter(contract -> contract.getContractId() == contractId)
                .findFirst()
                .orElse(null);
    }

    public void addContract(EmploymentContract contract) {
        contractDAO.insert(contract);
    }

    public void updateContract(EmploymentContract contract) {
        contractDAO.update(contract);
    }

    public void deleteContract(int contractId) {
        contractDAO.delete(contractId);
    }
}