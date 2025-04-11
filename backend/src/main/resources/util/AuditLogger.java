package main.java.hrms.human_resource_system.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditLogger {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void logChange(String entityType, int entityId, String action, int performedBy, Object oldValue, Object newValue) {
        String sql = "INSERT INTO AuditLog (entity_type, entity_id, action, performed_by, old_value, new_value) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entityType);
            ps.setInt(2, entityId);
            ps.setString(3, action);
            ps.setInt(4, performedBy);
            ps.setString(5, oldValue != null ? objectMapper.writeValueAsString(oldValue) : null);
            ps.setString(6, newValue != null ? objectMapper.writeValueAsString(newValue) : null);

            ps.executeUpdate();

        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
        }
    }
}