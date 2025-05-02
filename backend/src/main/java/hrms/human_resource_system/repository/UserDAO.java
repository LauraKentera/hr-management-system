package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Role;
import hrms.human_resource_system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RoleDAO roleDAO;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate, RoleDAO roleDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleDAO = roleDAO;
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = roleDAO.getById(rs.getInt("role_id"));
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                role,
                rs.getObject("employee_id") != null ? rs.getInt("employee_id") : null
        );
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM User";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all users", e);
        }
    }

    public User getById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving user with ID " + id, e);
        }
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, username);
        } catch (Exception e) {
            throw new DLException("Error retrieving user by username: " + username, e);
        }
    }

    public User getByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM User WHERE employee_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, employeeId);
        } catch (Exception e) {
            throw new DLException("Error retrieving user by employee ID: " + employeeId, e);
        }
    }

    public void insert(User user) {
        String sql = "INSERT INTO User (username, password, role_id, employee_id) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getId(),
                    user.getEmployeeId());
        } catch (Exception e) {
            throw new DLException("Error inserting user: " + user.getUsername(), e);
        }
    }

    public void update(int id, User user) {
        String sql = "UPDATE User SET username = ?, password = ?, role_id = ?, employee_id = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getId(),
                    user.getEmployeeId(),
                    id);
        } catch (Exception e) {
            throw new DLException("Error updating user with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting user with ID " + id, e);
        }
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking username uniqueness", e);
        }
    }

    public boolean employeeIdExists(int employeeId) {
        String sql = "SELECT COUNT(*) FROM User WHERE employee_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, employeeId);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking employee_id uniqueness", e);
        }
    }

    public boolean usernameTakenByOther(String username, int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ? AND id != ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, userId);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking username during update", e);
        }
    }

    public boolean employeeIdTakenByOther(int employeeId, int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE employee_id = ? AND id != ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, employeeId, userId);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking employee_id during update", e);
        }
    }
}
