package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EducationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class EducationLevelDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EducationLevelDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EducationLevel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EducationLevel(
                rs.getInt("education_level_id"),
                rs.getString("name"),
                rs.getInt("user_id"),
                rs.getTimestamp("modification_date").toLocalDateTime(),
                rs.getBoolean("is_active")
        );
    }

    public EducationLevel getById(int id) {
        String sql = "SELECT * FROM EducationLevel WHERE education_level_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving education level with ID " + id, e);
        }
    }

    public List<EducationLevel> getAll() {
        String sql = "SELECT * FROM EducationLevel";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all education levels", e);
        }
    }

    public void insert(EducationLevel educationLevel) {
        String sql = "INSERT INTO EducationLevel (name, user_id, modification_date, is_active) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    educationLevel.getName(),
                    educationLevel.getUserId(),
                    Timestamp.valueOf(educationLevel.getModificationDate()),
                    educationLevel.isActive()
            );
        } catch (Exception e) {
            throw new DLException("Error inserting education level: " + educationLevel.getName(), e);
        }
    }

    public void update(int id, EducationLevel educationLevel) {
        String sql = "UPDATE EducationLevel SET name = ?, user_id = ?, modification_date = ?, is_active = ? WHERE education_level_id = ?";
        try {
            jdbcTemplate.update(sql,
                    educationLevel.getName(),
                    educationLevel.getUserId(),
                    Timestamp.valueOf(educationLevel.getModificationDate()),
                    educationLevel.isActive(),
                    id
            );
        } catch (Exception e) {
            throw new DLException("Error updating education level with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EducationLevel WHERE education_level_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting education level with ID " + id, e);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM EducationLevel WHERE education_level_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking if education level exists with ID " + id, e);
        }
    }
}
