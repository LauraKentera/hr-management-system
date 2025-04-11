package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.repository.UserDAO;
import main.resources.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {

    private final UserDAO userDAO;

    // Constructor injection of UserDAO
    @Autowired
    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Authenticate user based on username and password
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @return Optional<User> containing user if authentication is successful, empty if failed
     */
    public Optional<User> authenticate(String username, String password) {
        // Validate inputs
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        // Retrieve the user by username
        User user = userDAO.getByUsername(username);

        // If user exists and password matches, return the user wrapped in Optional
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return Optional.of(user);
        }

        // Return empty Optional if authentication fails
        return Optional.empty();
    }

    /**
     * Wrapper method to simplify the authentication process and return a message
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @return String message indicating success or failure
     */
    public String login(String username, String password) {
        Optional<User> user = authenticate(username, password);

        // Return appropriate message based on authentication result
        return user.map(u -> "Login successful: Welcome " + u.getUsername() + "!")
                   .orElse("Login failed: Invalid username or password.");
    }
}
