package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.PayrollDTO;
import main.java.hrms.human_resource_system.model.Payroll;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayrollMapper {
    PayrollMapper INSTANCE = Mappers.getMapper(PayrollMapper.class);

    PayrollDTO payrollToPayrollDTO(Payroll payroll);
    Payroll payrollDTOToPayroll(PayrollDTO payrollDTO);
}
