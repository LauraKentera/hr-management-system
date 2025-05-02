package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmployeeChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class EmployeeChangeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeChangeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EmployeeChange mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmployeeChange(
                rs.getInt("change_id"),
                rs.getInt("employee_id"),
                rs.getDate("change_date").toLocalDate(),
                rs.getObject("old_position_id") != null ? rs.getInt("old_position_id") : null,
                rs.getInt("new_position_id"),
                rs.getBigDecimal("old_salary"),
                rs.getBigDecimal("new_salary")
        );
    }

    public EmployeeChange getById(int id) {
        String sql = "SELECT * FROM EmployeeChange WHERE change_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving EmployeeChange with ID " + id, e);
        }
    }

    public List<EmployeeChange> getAll() {
        String sql = "SELECT * FROM EmployeeChange";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all EmployeeChanges", e);
        }
    }

    public void insert(EmployeeChange change) {
        String sql = """
            INSERT INTO EmployeeChange 
            (employee_id, change_date, old_position_id, new_position_id, old_salary, new_salary)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, change.getEmployeeId());
                ps.setDate(2, Date.valueOf(change.getChangeDate()));
                if (change.getOldPositionId() != null)
                    ps.setInt(3, change.getOldPositionId());
                else
                    ps.setNull(3, Types.INTEGER);
                ps.setInt(4, change.getNewPositionId());
                if (change.getOldSalary() != null)
                    ps.setBigDecimal(5, change.getOldSalary());
                else
                    ps.setNull(5, Types.DECIMAL);
                ps.setBigDecimal(6, change.getNewSalary());
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error inserting EmployeeChange", e);
        }
    }

    public void update(EmployeeChange change) {
        String sql = """
            UPDATE EmployeeChange 
            SET employee_id = ?, change_date = ?, old_position_id = ?, new_position_id = ?, old_salary = ?, new_salary = ? 
            WHERE change_id = ?
        """;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, change.getEmployeeId());
                ps.setDate(2, Date.valueOf(change.getChangeDate()));
                if (change.getOldPositionId() != null)
                    ps.setInt(3, change.getOldPositionId());
                else
                    ps.setNull(3, Types.INTEGER);
                ps.setInt(4, change.getNewPositionId());
                if (change.getOldSalary() != null)
                    ps.setBigDecimal(5, change.getOldSalary());
                else
                    ps.setNull(5, Types.DECIMAL);
                ps.setBigDecimal(6, change.getNewSalary());
                ps.setInt(7, change.getChangeId());
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error updating EmployeeChange with ID " + change.getChangeId(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeChange WHERE change_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting EmployeeChange with ID " + id, e);
        }
    }
}
