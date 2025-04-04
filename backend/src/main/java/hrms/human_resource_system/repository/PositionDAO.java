package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO {

    public Position getById(int id) {
        String sql = "SELECT * FROM Position WHERE position_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Position position = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Position parent = null;
                int parentId = rs.getInt("parent_id");
                if (!rs.wasNull()) {
                    parent = getById(parentId); // Recursive fetch
                }

                position = new Position(
                    rs.getInt("position_id"),
                    parent,
                    rs.getString("name"),
                    rs.getString("short_name"),
                    rs.getInt("education_level_id"),
                    rs.getString("benefits"),
                    rs.getBoolean("requires_licensing"),
                    rs.getBoolean("is_active")
                );
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving position with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return position;
    }

    public List<Position> getAll() {
        String sql = "SELECT * FROM Position";
        List<Position> positions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Position parent = null;
                int parentId = rs.getInt("parent_id");
                if (!rs.wasNull()) {
                    parent = getById(parentId);
                }

                positions.add(new Position(
                    rs.getInt("position_id"),
                    parent,
                    rs.getString("name"),
                    rs.getString("short_name"),
                    rs.getInt("education_level_id"),
                    rs.getString("benefits"),
                    rs.getBoolean("requires_licensing"),
                    rs.getBoolean("is_active")
                ));
            }

        } catch (SQLException e) {
            throw new DLException("Error fetching all positions", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return positions;
    }

    public void insert(Position position) {
        String sql = "INSERT INTO Position (parent_id, name, short_name, education_level_id, benefits, requires_licensing, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            if (position.getParentPosition() != null) {
                ps.setInt(1, position.getParentPosition().getPositionId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, position.getName());
            ps.setString(3, position.getShortName());
            ps.setInt(4, position.getEducationLevelId());
            ps.setString(5, position.getBenefits());
            ps.setBoolean(6, position.isRequiresLicensing());
            ps.setBoolean(7, position.isActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error inserting position: " + position.getName(), e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Position WHERE position_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting position with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }
}
