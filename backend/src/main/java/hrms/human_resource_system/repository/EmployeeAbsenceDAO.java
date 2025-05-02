package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.AbsenceType;
import hrms.human_resource_system.model.EmployeeAbsence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeAbsenceDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AbsenceTypeDAO absenceTypeDAO;
    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeAbsenceDAO(JdbcTemplate jdbcTemplate,
                              AbsenceTypeDAO absenceTypeDAO,
                              EmployeeDAO employeeDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.absenceTypeDAO = absenceTypeDAO;
        this.employeeDAO = employeeDAO;
    }

    public EmployeeAbsence getById(int id) {
        String sql = "SELECT * FROM EmployeeAbsence WHERE absence_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapResultSet(rs), id);
        } catch (Exception e) {
            throw new DLException("Error retrieving absence with ID " + id, e);
        }
    }

    public List<EmployeeAbsence> getAll() {
        String sql = "SELECT * FROM EmployeeAbsence";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapResultSet(rs));
        } catch (Exception e) {
            throw new DLException("Error retrieving all employee absences", e);
        }
    }

    public List<EmployeeAbsence> getByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM EmployeeAbsence WHERE employee_id = ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapResultSet(rs), employeeId);
        } catch (Exception e) {
            throw new DLException("Error retrieving absences for employee ID " + employeeId, e);
        }
    }

    public void insert(EmployeeAbsence absence) {
        String sql = "INSERT INTO EmployeeAbsence (employee_id, absence_type_id, start_date, end_date, notes, status, approved_by) VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, absence.getEmployeeId());
                ps.setInt(2, absence.getAbsenceTypeId());
                ps.setDate(3, Date.valueOf(absence.getStartDate()));
                ps.setDate(4, Date.valueOf(absence.getEndDate()));
                ps.setString(5, absence.getNotes());
                ps.setString(6, absence.getStatus()); // Insert the status
                if (absence.getApprovedBy() != null) {
                    ps.setInt(7, absence.getApprovedBy());
                } else {
                    ps.setNull(7, Types.INTEGER);
                }
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                absence.setAbsenceId(keyHolder.getKey().intValue());
            }
        } catch (Exception e) {
            throw new DLException("Error inserting employee absence", e);
        }
    }

    public void update(int id, EmployeeAbsence absence) {
        String sql = "UPDATE EmployeeAbsence SET employee_id = ?, absence_type_id = ?, start_date = ?, end_date = ?, notes = ?, status = ?, approved_by = ? WHERE absence_id = ?";
        try {
            jdbcTemplate.update(sql,
                    absence.getEmployeeId(),
                    absence.getAbsenceTypeId(),
                    Date.valueOf(absence.getStartDate()),
                    Date.valueOf(absence.getEndDate()),
                    absence.getNotes(),
                    absence.getStatus(),  // Update the status
                    absence.getApprovedBy(),  // Update the approvedBy field
                    id
            );
        } catch (Exception e) {
            throw new DLException("Error updating employee absence with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeAbsence WHERE absence_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting employee absence with ID " + id, e);
        }
    }

    private EmployeeAbsence mapResultSet(ResultSet rs) throws SQLException {
        EmployeeAbsence ea = new EmployeeAbsence();
        ea.setAbsenceId(rs.getInt("absence_id"));
        ea.setEmployeeId(rs.getInt("employee_id"));
        ea.setAbsenceTypeId(rs.getInt("absence_type_id"));
        ea.setStartDate(rs.getDate("start_date").toLocalDate());
        ea.setEndDate(rs.getDate("end_date").toLocalDate());
        ea.setNotes(rs.getString("notes"));
        ea.setStatus(rs.getString("status")); // Map status
        ea.setApprovedBy(rs.getInt("approved_by")); // Map approvedBy

        // fetch and set AbsenceType and Employee
        ea.setAbsenceType(absenceTypeDAO.getById(ea.getAbsenceTypeId()));
        ea.setEmployee(employeeDAO.getById(ea.getEmployeeId()));

        return ea;
    }
}
