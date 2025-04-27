package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.EmployeeBankAccountRequestDTO;
import hrms.human_resource_system.dto.EmployeeBankAccountResponseDTO;
import hrms.human_resource_system.model.EmployeeBankAccount;

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
        return new EmployeeBankAccountResponseDTO(
                account.getEmployeeId(),
                account.getBankName(),
                account.getAccountNumber(),
                account.getIban()
        );
    }
}
