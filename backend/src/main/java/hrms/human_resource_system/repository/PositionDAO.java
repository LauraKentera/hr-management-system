package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Position;
import main.java.hrms.human_resource_system.util.AuditLogger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PositionDAO {

    public boolean existsById(int positionId) {
        String sql = "SELECT COUNT(*) FROM Position WHERE position_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, positionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DLException("Error checking if position exists with ID " + positionId, e);
        }
        return false;
    }

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
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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

            // Retrieve the generated ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    position.setPositionId(generatedKeys.getInt(1));
                }
            }

            // Log the change
            AuditLogger.logChange("Position", position.getPositionId(), "INSERT", performedBy, null, position);

        } catch (SQLException e) {
            throw new DLException("Error inserting position: " + position.getName(), e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Position WHERE position_id = ?";
        Position oldPosition = getById(id); // Fetch old data for logging
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            // Log the change
            AuditLogger.logChange("Position", id, "DELETE", performedBy, oldPosition, null);

        } catch (SQLException e) {
            throw new DLException("Error deleting position with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void update(Position position) {
        String sql = "UPDATE Position SET parent_id = ?, name = ?, short_name = ?, education_level_id = ?, " +
                "benefits = ?, requires_licensing = ?, is_active = ? WHERE position_id = ?";
        Position oldPosition = getById(position.getPositionId()); // Fetch old data for logging
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
            ps.setInt(8, position.getPositionId());

            ps.executeUpdate();

            // Log the change
            AuditLogger.logChange("Position", position.getPositionId(), "UPDATE", performedBy, oldPosition, position);

        } catch (SQLException e) {
            throw new DLException("Error updating position with ID " + position.getPositionId(), e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }
}
