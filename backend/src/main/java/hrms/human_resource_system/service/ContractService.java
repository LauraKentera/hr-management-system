package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.ContractAnnex;
import main.java.hrms.human_resource_system.model.EmploymentContract;
import main.java.hrms.human_resource_system.repository.ContractAnnexDAO;
import main.java.hrms.human_resource_system.repository.ContractDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.PositionDAO;

import java.util.List;
import java.util.function.Supplier;

public class ContractService {

    private final ContractDAO contractDAO;
    private final EmployeeDAO employeeDAO;
    private final PositionDAO positionDAO;
    private final ContractAnnexDAO contractAnnexDAO = new ContractAnnexDAO();

    public ContractService() {
        this.contractDAO = new ContractDAO();
        this.employeeDAO = new EmployeeDAO();
        this.positionDAO = new PositionDAO();
    }

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public List<EmploymentContract> getAllContracts() {
        return wrap(contractDAO::getAll);
    }

    public EmploymentContract getContractById(int contractId) {
        return wrap(() -> contractDAO.getAll().stream()
                .filter(contract -> contract.getContractId() == contractId)
                .findFirst()
                .orElse(null));
    }

    public void addContract(EmploymentContract contract) {
        wrap(() -> {
            validateContract(contract);
            contractDAO.insert(contract);
            return null;
        });
    }

    public void updateContract(EmploymentContract contract) {
        wrap(() -> {
            validateContract(contract);
            contractDAO.update(contract);
            return null;
        });
    }

    public void deleteContract(int contractId) {
        wrap(() -> {
            contractDAO.delete(contractId);
            return null;
        });
    }

    private void validateContract(EmploymentContract contract) {
        if (contract.getEndDate() != null && !contract.getStartDate().isBefore(contract.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        if (contract.getSalary().compareTo(new java.math.BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException("Salary must be greater than 0.");
        }

        if (!employeeDAO.existsById(contract.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + contract.getEmployeeId() + " does not exist.");
        }

        if (!positionDAO.existsById(contract.getPositionId())) {
            throw new IllegalArgumentException("Position with ID " + contract.getPositionId() + " does not exist.");
        }
    }

    public List<ContractAnnex> getAnnexesByContractId(int contractId) {
        return wrap(() -> contractAnnexDAO.getAnnexesByContractId(contractId));
    }
}
