package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.repository.RoleDAO;
import main.java.hrms.human_resource_system.repository.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();

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

    public void insert(User user) {
        // Validate user before insertion
        validateUser(user, -1);
        userDAO.insert(user);
    }

    public void update(int id, User user) {
        // Validate user before updating
        validateUser(user, id);
        userDAO.update(id, user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public User getUserById(int id) {
        return userDAO.getById(id);
    }

    public void deleteUser(int id) {
        userDAO.delete(id);
    }

}
