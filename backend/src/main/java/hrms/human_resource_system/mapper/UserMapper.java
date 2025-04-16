package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.UserCreateRequest;
import main.java.hrms.human_resource_system.dto.UserResponseDTO;
import main.java.hrms.human_resource_system.dto.UserUpdateRequestDTO;
import main.java.hrms.human_resource_system.model.User;

public class UserMapper {

    // Converts a UserCreateRequest DTO to a User model
    public static User toUser(UserCreateRequest request, Role role) {
        return new User(
                0,  // New User, so no ID yet
                request.getUsername(),
                request.getPassword(),
                role,  // Role is fetched separately by the Role ID
                request.getEmployeeId()  // If provided
        );
    }

    // Converts a UserUpdateRequestDTO to an existing User object (for updating)
    public static User toUser(UserUpdateRequestDTO request, Role role) {
        return new User(
                0,  // Placeholder, not used for updates
                request.getUsername(),
                request.getPassword(),
                role,  // Role is fetched separately by the Role ID
                0  // Placeholder, employee ID will not be changed here
        );
    }

    // Converts User to UserResponseDTO for use in responses
    public static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().getName(),  // Assuming Role has a `name` field
                user.getEmployeeId()
        );
    }
}
