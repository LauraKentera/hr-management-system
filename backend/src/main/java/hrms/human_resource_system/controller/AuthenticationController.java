package hrms.human_resource_system.controller;

import hrms.human_resource_system.dto.LoginRequestDTO;
import hrms.human_resource_system.model.User;
import hrms.human_resource_system.repository.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
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
            User user = userDAO.getByUsername(loginRequest.getUsername());

            if (user != null && BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing login: " + e.getMessage());
        }
    }
}
