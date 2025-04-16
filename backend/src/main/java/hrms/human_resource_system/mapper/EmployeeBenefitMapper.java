package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeBenefitRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeBenefitResponseDTO;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.EmployeeBenefit;
import main.java.hrms.human_resource_system.model.BenefitItem;

import java.time.LocalDate;

public class EmployeeBenefitMapper {

    public static EmployeeBenefit toEntity(EmployeeBenefitRequestDTO dto) {
        EmployeeBenefit eb = new EmployeeBenefit();

        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        eb.setEmployee(employee);

        BenefitItem benefit = new BenefitItem();
        benefit.setBenefitItemId(dto.getBenefitItemId());
        eb.setBenefit(benefit);

        eb.setFromDate(LocalDate.parse(dto.getStartDate()));
        eb.setToDate(dto.getEndDate() != null ? LocalDate.parse(dto.getEndDate()) : null);

        return eb;
    }


    public static EmployeeBenefitResponseDTO toDTO(EmployeeBenefit eb) {
        EmployeeBenefitResponseDTO dto = new EmployeeBenefitResponseDTO();
        dto.setId(eb.getEmployeeBenefitId());
        dto.setEmployeeId(eb.getEmployee().getId());
        dto.setBenefitItemId(eb.getBenefit().getBenefitId());
        dto.setStartDate(eb.getFromDate().toString());
        dto.setEndDate(eb.getToDate() != null ? eb.getToDate().toString() : null);
        return dto;
    }
}