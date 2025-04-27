package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.AbsenceType;
import hrms.human_resource_system.model.EmployeeAbsence;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeAbsenceDAO {

    AbsenceTypeDAO absenceTypeDAO = new AbsenceTypeDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();


    public EmployeeAbsence getById(int id) {
        String sql = "SELECT * FROM EmployeeAbsence WHERE absence_id = ?";
        EmployeeAbsence absence = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                absence = mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return absence;
    }

    public List<EmployeeAbsence> getAll() {
        String sql = "SELECT * FROM EmployeeAbsence";
        List<EmployeeAbsence> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<EmployeeAbsence> getByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM EmployeeAbsence WHERE employee_id = ?";
        List<EmployeeAbsence> list = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
    
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
    
        } catch (SQLException e) {
            throw new DLException("Error retrieving absences for employee ID " + employeeId, e);
        }
    
        return list;
    }
    

    public void insert(EmployeeAbsence absence) {
        String sql = "INSERT INTO EmployeeAbsence (employee_id, absence_type_id, start_date, end_date, notes) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, absence.getEmployeeId());
            ps.setInt(2, absence.getAbsenceTypeId());
            ps.setDate(3, Date.valueOf(absence.getStartDate()));
            ps.setDate(4, Date.valueOf(absence.getEndDate()));
            ps.setString(5, absence.getNotes());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeAbsence WHERE absence_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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

        // fetch and set AbsenceType
        AbsenceType type = absenceTypeDAO.getById(ea.getAbsenceTypeId());
        ea.setAbsenceType(type);

        ea.setEmployee(employeeDAO.getById(ea.getEmployeeId()));

        return ea;
    }


    public void update(int id, EmployeeAbsence absence) {
        String sql = "UPDATE EmployeeAbsence SET employee_id = ?, absence_type_id = ?, start_date = ?, end_date = ?, notes = ? WHERE absence_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, absence.getEmployeeId());
            ps.setInt(2, absence.getAbsenceTypeId());
            ps.setDate(3, Date.valueOf(absence.getStartDate()));
            ps.setDate(4, Date.valueOf(absence.getEndDate()));
            ps.setString(5, absence.getNotes());
            ps.setInt(6, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error updating employee absence with ID " + id, e);
        }
    }
}

