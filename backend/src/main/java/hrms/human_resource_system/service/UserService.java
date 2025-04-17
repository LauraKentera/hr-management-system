package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.repository.RoleDAO;
import main.java.hrms.human_resource_system.repository.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();

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

    // Method to validate the user input
    private void validateUser(User user, int userIdToUpdate) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }

        // Ensure that the username is not taken by another user (for both insert and update)
        User existingUser = userDAO.getByUsername(user.getUsername());
        if (existingUser != null && (userIdToUpdate == -1 || existingUser.getId() != userIdToUpdate)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Validate if the provided role exists
        if (roleDAO.getById(user.getRole().getId()) == null) {
            throw new IllegalArgumentException("Role does not exist.");
        }
    }

    // Create a new user
    public User createUser(String username, String password, int roleId, int employeeId) {
        return wrap(() -> {
            // Create a new User object
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password); // Assume password is hashed elsewhere
            newUser.setRole(roleDAO.getById(roleId)); // Assign role from RoleDAO
            newUser.setEmployeeId(employeeId); // Assuming employeeId is an integer representing the employee

            // Validate user before creation
            validateUser(newUser, -1); // -1 means no existing user to update

            // Save the user
            userDAO.insert(newUser);
            return newUser;
        });
    }

    // Update an existing user
    public User updateUser(int id, String username, String password, int roleId, int employeeId) {
        return wrap(() -> {
            // Fetch existing user
            User existingUser = userDAO.getById(id);
            if (existingUser == null) {
                throw new IllegalArgumentException("User not found.");
            }

            // Update user fields
            existingUser.setUsername(username);
            existingUser.setPassword(password); // Assume password is hashed elsewhere
            existingUser.setRole(roleDAO.getById(roleId)); // Assign role from RoleDAO
            existingUser.setEmployeeId(employeeId); // Assuming employeeId is an integer representing the employee

            // Validate user before updating
            validateUser(existingUser, id); // Pass the current ID to prevent username conflicts

            // Save the updated user
            userDAO.update(id, existingUser);
            return existingUser;
        });
    }

    // Get all users
    public List<User> getAllUsers() {
        return wrap(userDAO::getAll);
    }

    // Get a user by ID
    public User getUserById(int id) {
        return wrap(() -> userDAO.getById(id));
    }

    // Delete user by ID
    public void deleteUser(int id) {
        wrap(() -> {
            userDAO.delete(id);
            return null;  // Return type is Void
        });
    }
}
