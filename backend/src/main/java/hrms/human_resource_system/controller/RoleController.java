package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Role;
import main.java.hrms.human_resource_system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")  // URL path for the Role endpoints
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // GET all roles
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // GET role by ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST new role
    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        roleService.addRole(role);
        return ResponseEntity.status(201).body("Role created successfully.");
    }

    // PUT update role
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody Role role) {
        if (roleService.roleExistsById(id)) {
            role.setId(id);  // Ensure the role ID is correct before updating
            roleService.updateRole(role);
            return ResponseEntity.ok("Role updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        if (roleService.roleExistsById(id)) {
            roleService.deleteRole(id);
            return ResponseEntity.ok("Role deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
