package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeBankAccountRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeBankAccountResponseDTO;
import main.java.hrms.human_resource_system.model.EmployeeBankAccount;

public class EmployeeBankAccountMapper {

    public static EmployeeBankAccount toEntity(EmployeeBankAccountRequestDTO dto) {
        EmployeeBankAccount account = new EmployeeBankAccount();
        account.setEmployeeId(dto.getEmployeeId());
        account.setBankName(dto.getBankName());
        account.setAccountNumber(dto.getAccountNumber());
        account.setIban(dto.getIban());
        return account;
    }

    public static EmployeeBankAccountResponseDTO toDTO(EmployeeBankAccount account) {
        EmployeeBankAccountResponseDTO dto = new EmployeeBankAccountResponseDTO();
        dto.setId(account.getEmployeeId());
        dto.setEmployeeId(account.getEmployeeId());
        dto.setBankName(account.getBankName());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setIban(account.getIban());
        return dto;
    }
}
