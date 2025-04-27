package hrms.human_resource_system.service;

import hrms.human_resource_system.model.ContractAnnex;
import hrms.human_resource_system.model.EmploymentContract;
import hrms.human_resource_system.repository.ContractAnnexDAO;
import hrms.human_resource_system.repository.ContractDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.PositionDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class ContractService {

    private final ContractDAO contractDAO;
    private final EmployeeDAO employeeDAO;
    private final PositionDAO positionDAO;
    private final ContractAnnexDAO contractAnnexDAO;

    public ContractService() {
        this.contractDAO = new ContractDAO();
        this.employeeDAO = new EmployeeDAO();
        this.positionDAO = new PositionDAO();
        this.contractAnnexDAO = new ContractAnnexDAO(); // Ensure contractAnnexDAO is initialized
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

    public EmploymentContract addContract(EmploymentContract contract, int performedBy) {
        wrap(() -> {
            validateContract(contract);
            contractDAO.insert(contract, performedBy); // Insert contract with performedBy for audit logging
            return contract;
        });
        return contract;
    }

    public EmploymentContract updateContract(EmploymentContract contract, int performedBy) {
        wrap(() -> {
            validateContract(contract);
            contractDAO.update(contract, performedBy); // Update contract with performedBy for audit logging
            return contract;
        });
        return contract;
    }

    public void deleteContract(int contractId, int performedBy) {
        wrap(() -> {
            contractDAO.delete(contractId, performedBy); // Delete contract with performedBy for audit logging
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

    // Add a contract annex
    public ContractAnnex addContractAnnex(ContractAnnex annex, int performedBy) {
        wrap(() -> {
            if (!contractDAO.getAll().stream().anyMatch(contract -> contract.getContractId() == annex.getContractId())) {
                throw new IllegalArgumentException("Contract ID " + annex.getContractId() + " does not exist.");
            }
            contractAnnexDAO.insert(annex); // Insert contract annex with only the ContractAnnex object
            return annex;
        });
        return annex;
    }

    // Update a contract annex
    public ContractAnnex updateContractAnnex(ContractAnnex annex, int performedBy) {
        wrap(() -> {
            if (!contractDAO.getAll().stream().anyMatch(contract -> contract.getContractId() == annex.getContractId())) {
                throw new IllegalArgumentException("Contract ID " + annex.getContractId() + " does not exist.");
            }
            contractAnnexDAO.update(annex); // Update contract annex with only the ContractAnnex object
            return annex;
        });
        return annex;
    }

    // Delete a contract annex
    public void deleteContractAnnex(int annexId, int performedBy) {
        wrap(() -> {
            contractAnnexDAO.delete(annexId); // Delete contract annex with only the annexId
            return null;
        });
    }
}
