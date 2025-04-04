package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Role;
import main.java.hrms.human_resource_system.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
