package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeChangeRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeChangeResponseDTO;
import main.java.hrms.human_resource_system.model.EmployeeChange;

import java.time.LocalDate;

public class EmployeeChangeMapper {

    public static EmployeeChange toEntity(EmployeeChangeRequestDTO dto) {
        EmployeeChange entity = new EmployeeChange();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setChangeDate(LocalDate.parse(dto.getEffectiveDate())); // assuming it's in yyyy-MM-dd
        return entity;
    }

    public static EmployeeChangeResponseDTO toDTO(EmployeeChange entity) {
        EmployeeChangeResponseDTO dto = new EmployeeChangeResponseDTO();
        dto.setId(entity.getChangeId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEffectiveDate(entity.getChangeDate().toString());
        return dto;
    }
}
