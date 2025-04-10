package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Role;
import main.java.hrms.human_resource_system.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public List<Role> getAllRoles() {
        return roleDAO.getAll();
    }

    public Role getRoleById(int id) {
        return roleDAO.getById(id);
    }

    public void addRole(Role role) {
        // Validate role before adding
        validateRole(role);
        roleDAO.insert(role);
    }

    public void updateRole(Role role) {
        // Validate role before updating
        validateRole(role);
        roleDAO.update(role);
    }

    public void deleteRole(int id) {
        roleDAO.delete(id);
    }

    public boolean roleExistsById(int id) {
        return roleDAO.existsById(id);
    }

    // Validation method for Role
    private void validateRole(Role role) {
        if (role.getName() == null || role.getName().isEmpty()) {
            throw new IllegalArgumentException("Role name is required.");
        }
        // You can add additional validations if necessary (e.g., name length, uniqueness check)
    }
}
