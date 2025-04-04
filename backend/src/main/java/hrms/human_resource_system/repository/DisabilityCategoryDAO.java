package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.DisabilityCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisabilityCategoryDAO {

    public DisabilityCategory getById(int id) {
        String sql = "SELECT * FROM DisabilityCategory WHERE disability_category_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DisabilityCategory category = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                category = new DisabilityCategory(
                    rs.getInt("disability_category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("legal_code"),
                    rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving disability category with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return category;
    }

    public List<DisabilityCategory> getAll() {
        String sql = "SELECT * FROM DisabilityCategory";
        List<DisabilityCategory> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new DisabilityCategory(
                    rs.getInt("disability_category_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("legal_code"),
                    rs.getBoolean("is_active")
                ));
            }

        } catch (SQLException e) {
            throw new DLException("Error fetching all disability categories", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return list;
    }

    public void insert(DisabilityCategory category) {
        String sql = "INSERT INTO DisabilityCategory (name, description, legal_code, is_active) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setString(3, category.getLegalCode());
            ps.setBoolean(4, category.isActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error inserting disability category", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM DisabilityCategory WHERE disability_category_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting disability category with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }
}
