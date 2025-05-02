package hrms.human_resource_system.service;

import hrms.human_resource_system.model.User;
import hrms.human_resource_system.repository.RoleDAO;
import hrms.human_resource_system.repository.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    @Autowired
    public UserService(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    private void validateUser(User user, int userIdToUpdate) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }

        User existingUser = userDAO.getByUsername(user.getUsername());
        if (existingUser != null && (userIdToUpdate == -1 || existingUser.getId() != userIdToUpdate)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (roleDAO.getById(user.getRole().getId()) == null) {
            throw new IllegalArgumentException("Role does not exist.");
        }
    }

    public User createUser(String username, String password, int roleId) {
        return wrap(() -> {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hash password
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(hashedPassword);
            newUser.setRole(roleDAO.getById(roleId));

            validateUser(newUser, -1);
            userDAO.insert(newUser);
            return newUser;
        });
    }

    public User updateUser(int id, String username, String password, int roleId) {
        return wrap(() -> {
            User existingUser = userDAO.getById(id);
            if (existingUser == null) {
                throw new IllegalArgumentException("User not found.");
            }

            existingUser.setUsername(username);
            existingUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); // Hash password
            existingUser.setRole(roleDAO.getById(roleId));

            validateUser(existingUser, id);
            userDAO.update(id, existingUser);
            return existingUser;
        });
    }

    public List<User> getAllUsers() {
        return wrap(userDAO::getAll);
    }

    public User getUserById(int id) {
        return wrap(() -> userDAO.getById(id));
    }

    public void deleteUser(int id) {
        wrap(() -> {
            userDAO.delete(id);
            return null;
        });
    }
}
