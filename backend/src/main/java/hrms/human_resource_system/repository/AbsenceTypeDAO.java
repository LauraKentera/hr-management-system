package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.AbsenceType;
import hrms.human_resource_system.util.AuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class AbsenceTypeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogger auditLogger;

    @Autowired
    public AbsenceTypeDAO(JdbcTemplate jdbcTemplate, AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogger = auditLogger;
    }

    private AbsenceType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AbsenceType(
                rs.getInt("absence_type_id"),
                rs.getString("name"),
                rs.getString("code"),
                rs.getString("description"),
                rs.getBoolean("is_paid"),
                rs.getBoolean("requires_approval"),
                rs.getBoolean("is_active")
        );
    }

    public AbsenceType getById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM AbsenceType WHERE absence_type_id = ?",
                    this::mapRow,
                    id
            );
        } catch (Exception e) {
            throw new DLException("Error retrieving absence type with ID " + id, e);
        }
    }

    public List<AbsenceType> getAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM AbsenceType", this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all absence types", e);
        }
    }

    public void insert(AbsenceType absenceType, int performedBy) {
        String sql = "INSERT INTO AbsenceType (name, code, description, is_paid, requires_approval, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, absenceType.getName());
                ps.setString(2, absenceType.getCode());
                ps.setString(3, absenceType.getDescription());
                ps.setBoolean(4, absenceType.isPaid());
                ps.setBoolean(5, absenceType.isRequiresApproval());
                ps.setBoolean(6, absenceType.isActive());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                absenceType.setAbsenceTypeId(keyHolder.getKey().intValue());
            }

            auditLogger.logChange("AbsenceType", absenceType.getAbsenceTypeId(), "INSERT", performedBy, null, absenceType);
        } catch (Exception e) {
            throw new DLException("Error inserting absence type: " + absenceType.getName(), e);
        }
    }

    public void delete(int id, int performedBy) {
        AbsenceType absenceType = getById(id);
        String sql = "DELETE FROM AbsenceType WHERE absence_type_id = ?";

        try {
            jdbcTemplate.update(sql, id);
            auditLogger.logChange("AbsenceType", id, "DELETE", performedBy, absenceType, null);
        } catch (Exception e) {
            throw new DLException("Error deleting absence type with ID " + id, e);
        }
    }

    public void update(AbsenceType absenceType, int performedBy) {
        String sql = "UPDATE AbsenceType SET name = ?, code = ?, description = ?, is_paid = ?, requires_approval = ?, is_active = ? WHERE absence_type_id = ?";
        AbsenceType oldAbsenceType = getById(absenceType.getAbsenceTypeId());

        try {
            jdbcTemplate.update(sql,
                    absenceType.getName(),
                    absenceType.getCode(),
                    absenceType.getDescription(),
                    absenceType.isPaid(),
                    absenceType.isRequiresApproval(),
                    absenceType.isActive(),
                    absenceType.getAbsenceTypeId()
            );

            auditLogger.logChange("AbsenceType", absenceType.getAbsenceTypeId(), "UPDATE", performedBy, oldAbsenceType, absenceType);
        } catch (Exception e) {
            throw new DLException("Error updating absence type with ID " + absenceType.getAbsenceTypeId(), e);
        }
    }
}
