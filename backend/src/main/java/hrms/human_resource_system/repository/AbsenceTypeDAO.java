package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.AbsenceType;
import org.springframework.stereotype.Repository;
import main.resources.util.AuditLogger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AbsenceTypeDAO {

    public AbsenceType getById(int id) {
        String sql = "SELECT * FROM AbsenceType WHERE absence_type_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AbsenceType absenceType = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                absenceType = new AbsenceType(
                    rs.getInt("absence_type_id"),
                    rs.getString("name"),
                    rs.getString("code"),
                    rs.getString("description"),
                    rs.getBoolean("is_paid"),
                    rs.getBoolean("requires_approval"),
                    rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving absence type with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return absenceType;
    }

    public List<AbsenceType> getAll() {
        String sql = "SELECT * FROM AbsenceType";
        List<AbsenceType> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new AbsenceType(
                    rs.getInt("absence_type_id"),
                    rs.getString("name"),
                    rs.getString("code"),
                    rs.getString("description"),
                    rs.getBoolean("is_paid"),
                    rs.getBoolean("requires_approval"),
                    rs.getBoolean("is_active")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error fetching all absence types", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return list;
    }

    public void insert(AbsenceType absenceType, int performed_by) {
        String sql = "INSERT INTO AbsenceType (name, code, description, is_paid, requires_approval, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, absenceType.getName());
            ps.setString(2, absenceType.getCode());
            ps.setString(3, absenceType.getDescription());
            ps.setBoolean(4, absenceType.isPaid());
            ps.setBoolean(5, absenceType.isRequiresApproval());
            ps.setBoolean(6, absenceType.isActive());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    absenceType.setAbsenceTypeId(generatedKeys.getInt(1));
                }
            }
            AuditLogger.logChange("AbsenceType", absenceType.getAbsenceTypeId(), "INSERT", performedBy, null, absenceType);

        } catch (SQLException e) {
            throw new DLException("Error inserting absence type: " + absenceType.getName(), e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id, int performed_by) {
        AbsenceType absenceType = getById(id);
        String sql = "DELETE FROM AbsenceType WHERE absence_type_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            AuditLogger.logChange("AbsenceType", id, "DELETE", performed_by, absenceType, null);
        } catch (SQLException e) {
            throw new DLException("Error deleting absence type with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void update(AbsenceType absenceType, int performed_by) {
        String sql = "UPDATE AbsenceType SET name = ?, code = ?, description = ?, is_paid = ?, requires_approval = ?, is_active = ? WHERE absence_type_id = ?";
        AbsenceTypeDAO oldAbsenceType = getById(absenceType.getAbsenceTypeId());
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, absenceType.getName());
            ps.setString(2, absenceType.getCode());
            ps.setString(3, absenceType.getDescription());
            ps.setBoolean(4, absenceType.isPaid());
            ps.setBoolean(5, absenceType.isRequiresApproval());
            ps.setBoolean(6, absenceType.isActive());
            ps.setInt(7, absenceType.getAbsenceTypeId());

            ps.executeUpdate();
            AuditLogger.logChange("AbsenceType", absenceType.getAbsenceTypeId(), "UPDATE", performedBy, oldAbsenceType, absenceType);
            
        } catch (SQLException e) {
            throw new DLException("Error updating absence type with ID " + absenceType.getAbsenceTypeId(), e);
        }
    }
}
    