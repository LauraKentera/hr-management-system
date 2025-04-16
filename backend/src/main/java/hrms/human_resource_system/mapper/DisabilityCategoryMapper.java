package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.DisabilityCategoryResponseDTO;
import main.java.hrms.human_resource_system.model.DisabilityCategory;

public class DisabilityCategoryMapper {

    public static DisabilityCategoryResponseDTO toDTO(DisabilityCategory category) {
        DisabilityCategoryResponseDTO dto = new DisabilityCategoryResponseDTO();
        dto.setId(category.getDisabilityCategoryId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}

