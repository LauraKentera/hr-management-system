package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.EmployeeDisabilityRequestDTO;
import hrms.human_resource_system.dto.EmployeeDisabilityResponseDTO;
import hrms.human_resource_system.model.EmployeeDisability;

public class EmployeeDisabilityMapper {

    public static EmployeeDisability toEntity(EmployeeDisabilityRequestDTO dto) {
        EmployeeDisability entity = new EmployeeDisability();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDisabilityCategoryId(dto.getDisabilityCategoryId());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static EmployeeDisabilityResponseDTO toDTO(EmployeeDisability entity) {
        EmployeeDisabilityResponseDTO dto = new EmployeeDisabilityResponseDTO();
        dto.setId(entity.getEmployeeId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setDisabilityCategoryId(entity.getDisabilityCategoryId());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
