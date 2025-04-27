package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Role;
import hrms.human_resource_system.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    private final RoleDAO roleDAO = new RoleDAO();

    public User getById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Role role = roleDAO.getById(rs.getInt("role_id"));
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role
                );
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving user with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return user;
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Role role = roleDAO.getById(rs.getInt("role_id"));
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role
                ));
            }

        } catch (SQLException e) {
            throw new DLException("Error fetching all users", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return users;
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                Role role = roleDAO.getById(rs.getInt("role_id"));
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role
                );
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving user by username: " + username, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return user;
    }

    public void insert(User user) {
        String sql = "INSERT INTO User (username, password, role_id) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole().getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error inserting user: " + user.getUsername(), e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting user with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void update(int id, User user) {
        String sql = "UPDATE User SET username = ?, password = ?, role_id = ?, employee_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole().getId());
            ps.setInt(4, user.getEmployeeId());
            ps.setInt(5, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error updating user with ID " + id, e);
        }
    }

    public User getByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM User WHERE employee_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Role role = roleDAO.getById(rs.getInt("role_id"));
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        role,
                        rs.getInt("employee_id")
                );
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving user by employee ID: " + employeeId, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return user;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DLException("Error checking username uniqueness", e);
        }
    }

    public boolean employeeIdExists(int employeeId) {
        String sql = "SELECT COUNT(*) FROM User WHERE employee_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DLException("Error checking employee_id uniqueness", e);
        }
    }

    public boolean usernameTakenByOther(String username, int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ? AND id != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DLException("Error checking username during update", e);
        }
    }

    public boolean employeeIdTakenByOther(int employeeId, int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE employee_id = ? AND id != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DLException("Error checking employee_id during update", e);
        }
    }


}
