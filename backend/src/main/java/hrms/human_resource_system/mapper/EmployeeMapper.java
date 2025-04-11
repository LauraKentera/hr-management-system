package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeCreateRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeUpdateRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeResponseDTO;
import main.java.hrms.human_resource_system.model.Department;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.Position;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeCreateRequestDTO dto) {
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

        Department dept = new Department();
        dept.setDepartmentId(dto.getDepartmentId());
        e.setDepartment(dept);

        Position pos = new Position();
        pos.setPositionId(dto.getPositionId());
        e.setPosition(pos);

        return e;
    }


    public static EmployeeResponseDTO toDTO(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setFullName(e.getFirstName() + " " + e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPhoneNumber(e.getPhoneNumber());
        dto.setDepartmentName(e.getDepartment() != null ? e.getDepartment().getName() : "N/A");
        dto.setPositionName(e.getPosition() != null ? e.getPosition().getName() : "N/A");
        dto.setEmploymentStatus(e.getEmploymentStatus());
        return dto;
    }
}
