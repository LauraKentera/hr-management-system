package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Position;
import hrms.human_resource_system.util.AuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class PositionDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogger auditLogger;

    @Autowired
    public PositionDAO(JdbcTemplate jdbcTemplate, AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogger = auditLogger;
    }

    private Position mapRow(ResultSet rs, int rowNum) throws SQLException {
        Position parent = null;
        int parentId = rs.getInt("parent_id");
        if (!rs.wasNull()) {
            parent = getById(parentId); // Recursive fetch
        }
        return new Position(
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

    public boolean existsById(int positionId) {
        String sql = "SELECT COUNT(*) FROM Position WHERE position_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, positionId);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking if position exists with ID " + positionId, e);
        }
    }

    public Position getById(int id) {
        String sql = "SELECT * FROM Position WHERE position_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving position with ID " + id, e);
        }
    }

    public List<Position> getAll() {
        String sql = "SELECT * FROM Position";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all positions", e);
        }
    }

    public void insert(Position position, int performedBy) {
        String sql = """
            INSERT INTO Position 
            (parent_id, name, short_name, education_level_id, benefits, requires_licensing, is_active)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                position.setPositionId(keyHolder.getKey().intValue());
            }

            auditLogger.logChange("Position", position.getPositionId(), "INSERT", performedBy, null, position);

        } catch (Exception e) {
            throw new DLException("Error inserting position: " + position.getName(), e);
        }
    }

    public void update(Position position, int performedBy) {
        String sql = """
            UPDATE Position SET 
            parent_id = ?, name = ?, short_name = ?, education_level_id = ?, benefits = ?, 
            requires_licensing = ?, is_active = ?
            WHERE position_id = ?
        """;

        Position oldPosition = getById(position.getPositionId());

        try {
            jdbcTemplate.update(sql,
                    position.getParentPosition() != null ? position.getParentPosition().getPositionId() : null,
                    position.getName(),
                    position.getShortName(),
                    position.getEducationLevelId(),
                    position.getBenefits(),
                    position.isRequiresLicensing(),
                    position.isActive(),
                    position.getPositionId()
            );

            auditLogger.logChange("Position", position.getPositionId(), "UPDATE", performedBy, oldPosition, position);

        } catch (Exception e) {
            throw new DLException("Error updating position with ID " + position.getPositionId(), e);
        }
    }

    public void delete(int id, int performedBy) {
        String sql = "DELETE FROM Position WHERE position_id = ?";
        Position oldPosition = getById(id);

        try {
            jdbcTemplate.update(sql, id);
            auditLogger.logChange("Position", id, "DELETE", performedBy, oldPosition, null);
        } catch (Exception e) {
            throw new DLException("Error deleting position with ID " + id, e);
        }
    }
}
