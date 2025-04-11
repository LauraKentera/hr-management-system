package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeResponseDTO;
import main.java.hrms.human_resource_system.dto.EmployeeUpdateRequestDTO;
import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.Position;
import main.java.hrms.human_resource_system.service.DepartmentService;
import main.java.hrms.human_resource_system.service.PositionService;

public class EmployeeMapper {

    // Update Employee from DTO, handle department and position linking
    public static Employee toEntity(EmployeeUpdateRequestDTO dto, DepartmentService departmentService, PositionService positionService) {
        Employee e = new Employee();
        e.setPIN(dto.getPIN());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setBirthDate(dto.getBirthDate());
        e.setDateOfHire(dto.getDateOfHire());
        e.setPhoneNumber(dto.getPhoneNumber());
        e.setEmail(dto.getEmail());
        e.setAddress(dto.getAddress());
        e.setGender(dto.getGender());
        e.setNationality(dto.getNationalityId());
        e.setEmploymentStatus(dto.getEmploymentStatus());
        e.setEmergencyContactName(dto.getEmergencyContactName());
        e.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        e.setMaritalStatus(dto.getMaritalStatus());
        e.setEmploymentType(dto.getEmploymentType());
        e.setManager(dto.getManagerId());
        e.setTaxId(dto.getTaxId());
        e.setBankAccountNumber(dto.getBankAccountNumber());

        // Retrieve actual Department and Position from the database
        Department dept = departmentService.getDepartmentById(dto.getDepartmentId());
        e.setDepartment(dept);

        Position pos = positionService.getPositionById(dto.getPositionId());
        e.setPosition(pos);

        return e;
    }

    // Convert Employee to EmployeeResponseDTO
    public static EmployeeResponseDTO toDTO(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setFullName(e.getFirstName() + " " + e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPhoneNumber(e.getPhoneNumber());

        // Safely handle null departments and positions
        dto.setDepartmentName(e.getDepartment() != null ? e.getDepartment().getName() : "N/A");
        dto.setPositionName(e.getPosition() != null ? e.getPosition().getName() : "N/A");

        dto.setEmploymentStatus(e.getEmploymentStatus());
        return dto;
    }
}
