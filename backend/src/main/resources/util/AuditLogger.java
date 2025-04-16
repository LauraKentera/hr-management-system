package main.resources.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.hrms.human_resource_system.repository.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The AuditLogger class provides functionality to log changes to entities
 * in the system. The log records information about changes made to entities,
 * including the type of entity, the ID of the entity, the action performed,
 * the user who performed the action, and the old and new values of the entity.
 *
 * This class inserts the log information into the AuditLog table of the database
 * for future reference and auditing purposes.
 */
public class AuditLogger {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AuditLogger.class);

    /**
     * Logs a change made to an entity. This includes the entity type, entity ID,
     * the action performed, the user who performed the action, and the old and
     * new values of the entity.
     *
     * @param entityType The type of the entity (e.g., "Employee", "Department").
     * @param entityId   The ID of the entity being modified.
     * @param action     The action performed (e.g., "UPDATE", "INSERT", "DELETE").
     * @param performedBy The ID of the user who performed the action.
     * @param oldValue   The old value of the entity before the change (can be null).
     * @param newValue   The new value of the entity after the change (can be null).
     */
    public static void logChange(String entityType, int entityId, String action, int performedBy, Object oldValue, Object newValue) {
        String sql = "INSERT INTO AuditLog (entity_type, entity_id, action, performed_by, old_value, new_value) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entityType);
            ps.setInt(2, entityId);
            ps.setString(3, action);
            ps.setInt(4, performedBy);
            // Convert old and new values to JSON strings
            ps.setString(5, convertObjectToJson(oldValue));
            ps.setString(6, convertObjectToJson(newValue));

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error while logging audit change for entity: {}, ID: {}, action: {}. Exception: {}", entityType, entityId, action, e.getMessage(), e);
        }
    }

    /**
     * Converts an object to its JSON string representation.
     * If the object is null, it returns null. If conversion fails, it logs the error.
     *
     * @param obj The object to be converted to JSON.
     * @return The JSON string representation of the object, or null if conversion fails.
     */
    private static String convertObjectToJson(Object obj) {
        try {
            return obj != null ? objectMapper.writeValueAsString(obj) : null;
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Failed to convert object to JSON. Exception: {}", e.getMessage(), e);
            return null;  // Return null if conversion fails
        }
    }
}
