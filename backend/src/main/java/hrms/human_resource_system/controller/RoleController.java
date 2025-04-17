package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.RoleResponseDTO;
import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.model.Role;
import main.java.hrms.human_resource_system.mapper.RoleMapper;
import main.java.hrms.human_resource_system.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    // Constructor injection
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Role> roles = roleService.getAllRoles();
            List<RoleResponseDTO> roleDTOs = roles.stream()
                    .map(RoleMapper::toDTO)  // Using the mapper to convert Role to RoleResponseDTO
                    .collect(Collectors.toList());
            return ResponseEntity.ok(roleDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving roles", 500));
        }
    }

    // Get role by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable int id) {
        try {
            Role role = roleService.getRoleById(id);
            if (role != null) {
                RoleResponseDTO roleDTO = RoleMapper.toDTO(role);  // Using the mapper here too
                return ResponseEntity.ok(roleDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomErrorResponse("Role not found with ID: " + id, 404));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving role", 500));
        }
    }

    // Create a new role
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            roleService.addRole(role);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("✅ Role created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating role", 500));
        }
    }

    // Update an existing role
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable int id, @RequestBody Role role) {
        try {
            role.setId(id); // Ensure the path ID matches the body ID
            roleService.updateRole(role);
            return ResponseEntity.ok("✏️ Role updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating role", 500));
        }
    }

    // Delete a role
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable int id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.ok("🗑️ Role deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error deleting role", 500));
        }
    }
}
