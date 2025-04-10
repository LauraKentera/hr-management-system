package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.repository.UserDAO;
import main.resources.util.PasswordUtil;

public class LoginService {

    private final UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {
        // Validate inputs
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        User user = userDAO.getByUsername(username);

        // Check if user exists and if password matches
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }

        return null;  // Return null if authentication fails
    }
}
