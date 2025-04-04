package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.repository.UserDAO;
import main.resources.util.PasswordUtil;

public class LoginService {

    private final UserDAO userDAO = new UserDAO();

    public User authenticate(String username, String password) {
        User user = userDAO.getByUsername(username); // we'll create this method next
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
