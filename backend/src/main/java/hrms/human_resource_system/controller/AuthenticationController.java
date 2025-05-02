package hrms.human_resource_system.controller;

import hrms.human_resource_system.dto.LoginRequestDTO;
import hrms.human_resource_system.dto.SimpleLoginResponse;
import hrms.human_resource_system.model.User;
import hrms.human_resource_system.repository.UserDAO;
import hrms.human_resource_system.util.PasswordUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserDAO userDAO;

    public AuthenticationController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            System.out.println("Username input: " + loginRequest.getUsername());
            System.out.println("Password input: " + loginRequest.getPassword());

            User user = userDAO.getByUsername(loginRequest.getUsername());

            if (user == null) {
                System.out.println("User not found.");
                return ResponseEntity.status(401).body("Invalid username or password");
            }

            System.out.println("User found: " + user.getUsername());
            System.out.println("Password match: " + PasswordUtil.checkPassword(loginRequest.getPassword(), user.getPassword()));

            if (PasswordUtil.checkPassword(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(new SimpleLoginResponse(user.getRole().getName(), user.getId()));
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing login: " + e.getMessage());
        }
    }
}
