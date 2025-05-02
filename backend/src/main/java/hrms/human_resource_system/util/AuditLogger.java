package hrms.human_resource_system.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hrms.human_resource_system.exception.DLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuditLogger {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogger.class);
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditLogger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logChange(String entityType, int entityId, String action, int performedBy, Object oldValue, Object newValue) {
        String sql = """
            INSERT INTO AuditLog (entity_type, entity_id, action, performed_by, old_value, new_value)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try {
            jdbcTemplate.update(sql,
                    entityType,
                    entityId,
                    action,
                    performedBy,
                    convertObjectToJson(oldValue),
                    convertObjectToJson(newValue)
            );
        } catch (Exception e) {
            logger.error("❌ Failed to log audit change for {} ID {}: {}", entityType, entityId, e.getMessage(), e);
            throw new DLException("Audit logging failed", e);
        }
    }

    private String convertObjectToJson(Object obj) {
        try {
            return obj != null ? objectMapper.writeValueAsString(obj) : null;
        } catch (Exception e) {
            logger.error("❌ Failed to convert object to JSON: {}", e.getMessage(), e);
            return null;
        }
    }
}
