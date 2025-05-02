package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Nationality;
import hrms.human_resource_system.util.AuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class NationalityDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogger auditLogger;

    @Autowired
    public NationalityDAO(JdbcTemplate jdbcTemplate, AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogger = auditLogger;
    }

    private Nationality mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Nationality(
                rs.getInt("nationality_id"),
                rs.getString("name")
        );
    }

    public Nationality getById(int id) {
        String sql = "SELECT * FROM Nationality WHERE nationality_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving nationality with ID " + id, e);
        }
    }

    public List<Nationality> getAll() {
        String sql = "SELECT * FROM Nationality";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all nationalities", e);
        }
    }

    public void insert(Nationality nationality, int performedBy) {
        String sql = "INSERT INTO Nationality (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nationality.getName());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                nationality.setNationalityId(keyHolder.getKey().intValue());
            }

            auditLogger.logChange("Nationality", nationality.getNationalityId(), "INSERT", performedBy, null, nationality);
        } catch (Exception e) {
            throw new DLException("Error inserting nationality: " + nationality.getName(), e);
        }
    }

    public void update(Nationality nationality, int performedBy) {
        String sql = "UPDATE Nationality SET name = ? WHERE nationality_id = ?";
        Nationality oldNationality = getById(nationality.getNationalityId());

        try {
            jdbcTemplate.update(sql, nationality.getName(), nationality.getNationalityId());
            auditLogger.logChange("Nationality", nationality.getNationalityId(), "UPDATE", performedBy, oldNationality, nationality);
        } catch (Exception e) {
            throw new DLException("Error updating nationality with ID " + nationality.getNationalityId(), e);
        }
    }

    public void delete(int id, int performedBy) {
        String sql = "DELETE FROM Nationality WHERE nationality_id = ?";
        Nationality oldNationality = getById(id);

        try {
            jdbcTemplate.update(sql, id);
            auditLogger.logChange("Nationality", id, "DELETE", performedBy, oldNationality, null);
        } catch (Exception e) {
            throw new DLException("Error deleting nationality with ID " + id, e);
        }
    }
}
