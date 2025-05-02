package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Benefit;
import hrms.human_resource_system.util.AuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BenefitDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogger auditLogger;

    @Autowired
    public BenefitDAO(JdbcTemplate jdbcTemplate, AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogger = auditLogger;
    }

    private Benefit mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Benefit(
                rs.getInt("benefit_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("is_taxable"),
                rs.getBoolean("is_active")
        );
    }

    public Benefit getById(int id) {
        String sql = "SELECT * FROM Benefit WHERE benefit_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving benefit with ID " + id, e);
        }
    }

    public List<Benefit> getAll() {
        String sql = "SELECT * FROM Benefit";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error retrieving all benefits", e);
        }
    }

    public void insert(Benefit benefit, int performedBy) {
        String sql = "INSERT INTO Benefit (name, description, is_taxable, is_active) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, benefit.getName());
                ps.setString(2, benefit.getDescription());
                ps.setBoolean(3, benefit.isTaxable());
                ps.setBoolean(4, benefit.isActive());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                benefit.setBenefitId(keyHolder.getKey().intValue());
            }

            auditLogger.logChange("Benefit", benefit.getBenefitId(), "INSERT", performedBy, null, benefit);
        } catch (Exception e) {
            throw new DLException("Error inserting benefit", e);
        }
    }

    public void delete(int id, int performedBy) {
        Benefit oldBenefit = getById(id);
        String sql = "DELETE FROM Benefit WHERE benefit_id = ?";

        try {
            jdbcTemplate.update(sql, id);
            auditLogger.logChange("Benefit", id, "DELETE", performedBy, oldBenefit, null);
        } catch (Exception e) {
            throw new DLException("Error deleting benefit with ID " + id, e);
        }
    }

    public void update(int id, Benefit benefit, int performedBy) {
        String sql = "UPDATE Benefit SET name = ?, description = ?, is_taxable = ?, is_active = ? WHERE benefit_id = ?";
        Benefit oldBenefit = getById(id);

        try {
            jdbcTemplate.update(sql,
                    benefit.getName(),
                    benefit.getDescription(),
                    benefit.isTaxable(),
                    benefit.isActive(),
                    id
            );

            auditLogger.logChange("Benefit", id, "UPDATE", performedBy, oldBenefit, benefit);
        } catch (Exception e) {
            throw new DLException("Error updating benefit with ID " + id, e);
        }
    }
}
