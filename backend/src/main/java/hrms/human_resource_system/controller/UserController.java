package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.model.User;
import main.java.hrms.human_resource_system.repository.UserDAO;
import main.resources.util.PasswordUtil;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import main.java.hrms.human_resource_system.dto.UserCreateRequest;
import main.java.hrms.human_resource_system.model.Role;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDAO userDAO = new UserDAO();

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        if (userDAO.usernameExists(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse("Username already exists", 409));
        }

        if (userDAO.employeeIdExists(request.getEmployeeId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse("Employee already linked to another user", 409));
        }

        Role role = new Role();
        role.setId(request.getRoleId());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
        user.setRole(role);
        user.setEmployeeId(request.getEmployeeId());

        userDAO.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ User created.");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userDAO.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userDAO.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userDAO.delete(id);
        return ResponseEntity.ok("🗑️ User with ID " + id + " deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserCreateRequest request) {
        if (userDAO.usernameTakenByOther(request.getUsername(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse("Username already taken by another user", 409));
        }

        if (userDAO.employeeIdTakenByOther(request.getEmployeeId(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse("Employee already linked to another user", 409));
        }

        Role role = new Role();
        role.setId(request.getRoleId());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
        user.setRole(role);
        user.setEmployeeId(request.getEmployeeId());

        userDAO.update(id, user);
        return ResponseEntity.ok("✅ User updated.");
    }


}
