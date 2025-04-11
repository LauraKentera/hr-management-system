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

    // Insert new user after validation
    public void insert(User user) {
        wrap(() -> {
            validateUser(user, -1); // Validate user before insertion
            userDAO.insert(user);
            return null;  // Return type is Void
        });
    }

    // Update existing user after validation
    public void update(int id, User user) {
        wrap(() -> {
            validateUser(user, id); // Validate user before updating
            userDAO.update(id, user);
            return null;  // Return type is Void
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
