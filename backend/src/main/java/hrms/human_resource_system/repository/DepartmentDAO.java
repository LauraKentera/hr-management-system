package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class DepartmentDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Department(
                rs.getInt("department_id"),
                rs.getString("name"),
                rs.getInt("manager_id")
        );
    }

    public Department getById(int id) {
        String sql = "SELECT * FROM Department WHERE department_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving department with ID " + id, e);
        }
    }

    public List<Department> getAll() {
        String sql = "SELECT * FROM Department WHERE is_deleted = FALSE";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all departments", e);
        }
    }

    public void insert(Department department) {
        String sql = "INSERT INTO Department (name, manager_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, department.getName());
                if (department.getManagerId() != null) {
                    ps.setInt(2, department.getManagerId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                department.setDepartmentId(keyHolder.getKey().intValue());
            }

        } catch (Exception e) {
            throw new DLException("Error inserting department: " + department.getName(), e);
        }
    }

    public void update(int id, Department department, int performedBy) {
        String sql = "UPDATE Department SET name = ?, manager_id = ? WHERE department_id = ?";
        try {
            jdbcTemplate.update(sql,
                    department.getName(),
                    department.getManagerId(),
                    id
            );
        } catch (Exception e) {
            throw new DLException("Error updating department with ID " + id, e);
        }
    }

    public void delete(int departmentId) {
        String sql = "UPDATE Department SET is_deleted = TRUE WHERE department_id = ?";

        try {
            jdbcTemplate.update(sql, departmentId);
        } catch (Exception e) {
            throw new DLException("Error deleting department with ID " + departmentId, e);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM Department WHERE department_id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking existence of department with ID " + id, e);
        }
    }
}
