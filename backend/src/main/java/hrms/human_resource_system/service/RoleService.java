package hrms.human_resource_system.service;

import hrms.human_resource_system.model.Role;
import hrms.human_resource_system.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return wrap(roleDAO::getAll);
    }

    // Get role by ID
    public Role getRoleById(int id) {
        return wrap(() -> roleDAO.getById(id));
    }

    // Add a new role
    public void addRole(Role role) {
        wrap(() -> {
            validateRole(role);  // Validate before adding
            roleDAO.insert(role);
            return null;  // Return type is Void
        });
    }

    // Update an existing role
    public void updateRole(Role role) {
        wrap(() -> {
            validateRole(role);  // Validate before updating
            roleDAO.update(role);
            return null;  // Return type is Void
        });
    }

    // Delete role by ID
    public void deleteRole(int id) {
        wrap(() -> {
            roleDAO.delete(id);
            return null;  // Return type is Void
        });
    }

    // Check if role exists by ID
    public boolean roleExistsById(int id) {
        return wrap(() -> roleDAO.existsById(id));
    }

    // Validation method for Role
    private void validateRole(Role role) {
        if (role.getName() == null || role.getName().isEmpty()) {
            throw new IllegalArgumentException("Role name is required.");
        }
        // Add other validations if necessary (e.g., name length, uniqueness check)
    }
}
