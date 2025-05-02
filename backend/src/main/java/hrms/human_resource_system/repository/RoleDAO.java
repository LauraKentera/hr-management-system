package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Role(rs.getInt("id"), rs.getString("name"));
    }

    public List<Role> getAll() {
        String sql = "SELECT * FROM Role";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error retrieving roles", e);
        }
    }

    public Role getById(int id) {
        String sql = "SELECT * FROM Role WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving role with ID " + id, e);
        }
    }

    public void insert(Role role) {
        String sql = "INSERT INTO Role (name) VALUES (?)";
        try {
            jdbcTemplate.update(sql, role.getName());
        } catch (Exception e) {
            throw new DLException("Error inserting role: " + role.getName(), e);
        }
    }

    public void update(Role role) {
        String sql = "UPDATE Role SET name = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, role.getName(), role.getId());
        } catch (Exception e) {
            throw new DLException("Error updating role with ID " + role.getId(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Role WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting role with ID " + id, e);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM Role WHERE id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking if role exists with ID " + id, e);
        }
    }
}
