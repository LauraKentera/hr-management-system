package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.AbsenceTypeResponseDTO;
import main.java.hrms.human_resource_system.model.AbsenceType;

public class AbsenceTypeMapper {

    public static AbsenceTypeResponseDTO toDTO(AbsenceType type) {
        AbsenceTypeResponseDTO dto = new AbsenceTypeResponseDTO();
        dto.setId(type.getAbsenceTypeId());
        dto.setName(type.getName());
        dto.setDescription(type.getDescription());
        return dto;
    }
}
