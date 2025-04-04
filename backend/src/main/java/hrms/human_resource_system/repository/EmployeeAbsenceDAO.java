package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.EmployeeAbsence;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAbsenceDAO {

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
        return new EmployeeAbsence(
                rs.getInt("absence_id"),
                rs.getInt("employee_id"),
                rs.getInt("absence_type_id"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getString("notes")
        );
    }
}

