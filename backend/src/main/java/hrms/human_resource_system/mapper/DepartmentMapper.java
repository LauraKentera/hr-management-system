package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.DepartmentRequestDTO;
import main.java.hrms.human_resource_system.dto.DepartmentResponseDTO;
import main.java.hrms.human_resource_system.model.Department;

public class DepartmentMapper {

    public static Department toEntity(DepartmentRequestDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        return department;
    }

    public static DepartmentResponseDTO toDTO(Department department) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(department.getDepartmentId());
        dto.setName(department.getName());
        return dto;
    }
}
