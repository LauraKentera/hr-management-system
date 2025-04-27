package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.RoleResponseDTO;
import hrms.human_resource_system.model.Role;

public class RoleMapper {

    // Method to map Role entity to RoleResponseDTO
    public static RoleResponseDTO toDTO(Role role) {
        return new RoleResponseDTO(role.getId(), role.getName());
    }
}
