package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Benefit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BenefitDAO {

    public Benefit getById(int id) {
        String sql = "SELECT * FROM Benefit WHERE benefit_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Benefit benefit = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                benefit = new Benefit(
                    rs.getInt("benefit_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBoolean("is_taxable"),
                    rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving benefit with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return benefit;
    }

    public List<Benefit> getAll() {
        String sql = "SELECT * FROM Benefit";
        List<Benefit> benefits = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                benefits.add(new Benefit(
                    rs.getInt("benefit_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBoolean("is_taxable"),
                    rs.getBoolean("is_active")
                ));
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving all benefits", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return benefits;
    }

    public void insert(Benefit benefit) {
        String sql = "INSERT INTO Benefit (name, description, is_taxable, is_active) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, benefit.getName());
            ps.setString(2, benefit.getDescription());
            ps.setBoolean(3, benefit.isTaxable());
            ps.setBoolean(4, benefit.isActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error inserting benefit", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Benefit WHERE benefit_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting benefit with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }
}
