package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.service.UserService;
import main.java.hrms.human_resource_system.dto.UserCreateRequest;
import main.java.hrms.human_resource_system.model.Role;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService; // Inject UserService

    // Constructor Injection for UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        try {
            // Use UserService to create the user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());  // Password will be hashed in service
            user.setRole(new Role(request.getRoleId()));
            user.setEmployeeId(request.getEmployeeId());

            userService.insert(user);  // Service handles validation and insertion

            return ResponseEntity.status(HttpStatus.CREATED).body("✅ User created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse(e.getMessage(), 409));
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);  // Delegate to service
        return ResponseEntity.ok("🗑️ User with ID " + id + " deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserCreateRequest request) {
        try {
            // Use UserService to update the user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());  // Password will be hashed in service
            user.setRole(new Role(request.getRoleId()));
            user.setEmployeeId(request.getEmployeeId());

            userService.update(id, user);  // Service handles validation and update

            return ResponseEntity.ok("✅ User updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse(e.getMessage(), 409));
        }
    }
}
