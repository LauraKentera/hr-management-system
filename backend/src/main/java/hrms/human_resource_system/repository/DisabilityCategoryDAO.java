package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.DisabilityCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class DisabilityCategoryDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DisabilityCategoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private DisabilityCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DisabilityCategory(
                rs.getInt("disability_category_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("legal_code"),
                rs.getBoolean("is_active")
        );
    }

    public DisabilityCategory getById(int id) {
        String sql = "SELECT * FROM DisabilityCategory WHERE disability_category_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving disability category with ID " + id, e);
        }
    }

    public List<DisabilityCategory> getAll() {
        String sql = "SELECT * FROM DisabilityCategory";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all disability categories", e);
        }
    }

    public void insert(DisabilityCategory category) {
        String sql = "INSERT INTO DisabilityCategory (name, description, legal_code, is_active) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    category.getName(),
                    category.getDescription(),
                    category.getLegalCode(),
                    category.isActive()
            );
        } catch (Exception e) {
            throw new DLException("Error inserting disability category", e);
        }
    }

    public void update(int id, DisabilityCategory category) {
        String sql = "UPDATE DisabilityCategory SET name = ?, description = ?, legal_code = ?, is_active = ? WHERE disability_category_id = ?";
        try {
            jdbcTemplate.update(sql,
                    category.getName(),
                    category.getDescription(),
                    category.getLegalCode(),
                    category.isActive(),
                    id
            );
        } catch (Exception e) {
            throw new DLException("Error updating disability category with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM DisabilityCategory WHERE disability_category_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting disability category with ID " + id, e);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM DisabilityCategory WHERE disability_category_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking if disability category exists with ID " + id, e);
        }
    }
}
