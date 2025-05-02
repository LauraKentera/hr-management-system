package hrms.human_resource_system.controller;

import hrms.human_resource_system.dto.LoginRequestDTO;
import hrms.human_resource_system.model.User;
import hrms.human_resource_system.repository.UserDAO;
import hrms.human_resource_system.security.JwtLoginResponse;
import hrms.human_resource_system.security.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserDAO userDAO;
    private final JwtUtils jwtUtils;

    public AuthenticationController(UserDAO userDAO, JwtUtils jwtUtils) {
        this.userDAO = userDAO;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            User user = userDAO.getByUsername(loginRequest.getUsername());

            if (user != null && BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtUtils.generateJwtToken(user.getUsername());

                // Add role and id to response
                return ResponseEntity.ok(new JwtLoginResponse(token, user.getRole().getName(), user.getId()));
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing login: " + e.getMessage());
        }
    }
}
