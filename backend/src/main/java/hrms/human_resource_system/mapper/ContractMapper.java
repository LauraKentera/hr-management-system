package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.ContractResponseDTO;
import hrms.human_resource_system.model.EmploymentContract;
import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.model.Position;

public class ContractMapper {

    public static ContractResponseDTO toDTO(EmploymentContract contract, String employeeName, String positionName) {
        ContractResponseDTO dto = new ContractResponseDTO();
        dto.setContractId(contract.getContractId());
        dto.setEmployeeId(contract.getEmployeeId());
        dto.setEmployeeName(employeeName);  // fetched via EmployeeDAO
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        dto.setPositionId(contract.getPositionId());
        dto.setPositionName(positionName);  // fetched via PositionDAO
        dto.setSalary(contract.getSalary());
        dto.setContractType(contract.getContractType());
        dto.setSignedDate(contract.getSignedDate());
        dto.setDocumentPath(contract.getDocumentPath());
        return dto;
    }
}
