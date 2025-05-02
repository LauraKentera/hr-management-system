package hrms.human_resource_system.service;

import hrms.human_resource_system.model.ContractAnnex;
import hrms.human_resource_system.model.EmploymentContract;
import hrms.human_resource_system.repository.ContractAnnexDAO;
import hrms.human_resource_system.repository.ContractDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.PositionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class ContractService {

    private final ContractDAO contractDAO;
    private final EmployeeDAO employeeDAO;
    private final PositionDAO positionDAO;
    private final ContractAnnexDAO contractAnnexDAO;

    @Autowired
    public ContractService(ContractDAO contractDAO,
                           EmployeeDAO employeeDAO,
                           PositionDAO positionDAO,
                           ContractAnnexDAO contractAnnexDAO) {
        this.contractDAO = contractDAO;
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
        this.contractAnnexDAO = contractAnnexDAO;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
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
            contractDAO.insert(contract, performedBy);
            return contract;
        });
        return contract;
    }

    public EmploymentContract updateContract(EmploymentContract contract, int performedBy) {
        wrap(() -> {
            validateContract(contract);
            contractDAO.update(contract, performedBy);
            return contract;
        });
        return contract;
    }

    public void deleteContract(int contractId, int performedBy) {
        wrap(() -> {
            contractDAO.delete(contractId, performedBy);
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

    public ContractAnnex addContractAnnex(ContractAnnex annex, int performedBy) {
        wrap(() -> {
            boolean exists = contractDAO.getAll().stream()
                    .anyMatch(contract -> contract.getContractId() == annex.getContractId());
            if (!exists) {
                throw new IllegalArgumentException("Contract ID " + annex.getContractId() + " does not exist.");
            }
            contractAnnexDAO.insert(annex);
            return annex;
        });
        return annex;
    }

    public ContractAnnex updateContractAnnex(ContractAnnex annex, int performedBy) {
        wrap(() -> {
            boolean exists = contractDAO.getAll().stream()
                    .anyMatch(contract -> contract.getContractId() == annex.getContractId());
            if (!exists) {
                throw new IllegalArgumentException("Contract ID " + annex.getContractId() + " does not exist.");
            }
            contractAnnexDAO.update(annex);
            return annex;
        });
        return annex;
    }

    public void deleteContractAnnex(int annexId, int performedBy) {
        wrap(() -> {
            contractAnnexDAO.delete(annexId);
            return null;
        });
    }
}
