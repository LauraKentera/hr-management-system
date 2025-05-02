package hrms.human_resource_system.repository;

import hrms.human_resource_system.model.EmployeeDisability;
import hrms.human_resource_system.exception.DLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeDisabilityDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDisabilityDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EmployeeDisability mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmployeeDisability(
                rs.getInt("employee_disability_id"),
                rs.getInt("employee_id"),
                rs.getInt("disability_category_id"),
                rs.getString("official_code"),
                rs.getDate("from_date").toLocalDate(),
                rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null,
                rs.getString("description"),
                rs.getObject("percentage") != null ? rs.getInt("percentage") : null,
                rs.getBoolean("is_active")
        );
    }

    public EmployeeDisability getById(int id) {
        String sql = "SELECT * FROM EmployeeDisability WHERE employee_disability_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving EmployeeDisability with ID " + id, e);
        }
    }

    public List<EmployeeDisability> getAll() {
        String sql = "SELECT * FROM EmployeeDisability";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all EmployeeDisabilities", e);
        }
    }

    public void insert(EmployeeDisability d) {
        String sql = """
            INSERT INTO EmployeeDisability 
            (employee_id, disability_category_id, official_code, from_date, to_date, description, percentage, is_active)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, d.getEmployeeId());
                ps.setInt(2, d.getDisabilityCategoryId());
                ps.setString(3, d.getOfficialCode());
                ps.setDate(4, Date.valueOf(d.getFromDate()));
                ps.setObject(5, d.getToDate() != null ? Date.valueOf(d.getToDate()) : null, Types.DATE);
                ps.setString(6, d.getDescription());
                ps.setObject(7, d.getPercentage(), Types.INTEGER);
                ps.setBoolean(8, d.isActive());
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error inserting EmployeeDisability", e);
        }
    }

    public void update(int id, EmployeeDisability d) {
        String sql = """
            UPDATE EmployeeDisability SET employee_id = ?, disability_category_id = ?, official_code = ?, 
            from_date = ?, to_date = ?, description = ?, percentage = ?, is_active = ? 
            WHERE employee_disability_id = ?
        """;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, d.getEmployeeId());
                ps.setInt(2, d.getDisabilityCategoryId());
                ps.setString(3, d.getOfficialCode());
                ps.setDate(4, Date.valueOf(d.getFromDate()));
                ps.setObject(5, d.getToDate() != null ? Date.valueOf(d.getToDate()) : null, Types.DATE);
                ps.setString(6, d.getDescription());
                ps.setObject(7, d.getPercentage(), Types.INTEGER);
                ps.setBoolean(8, d.isActive());
                ps.setInt(9, id);
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error updating EmployeeDisability with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeDisability WHERE employee_disability_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting EmployeeDisability with ID " + id, e);
        }
    }
}
