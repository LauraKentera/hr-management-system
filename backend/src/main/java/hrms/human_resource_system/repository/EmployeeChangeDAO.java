package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.EmployeeChange;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class EmployeeChangeDAO {

    public EmployeeChange getById(int id) {
        String sql = "SELECT * FROM EmployeeChange WHERE change_id = ?";
        EmployeeChange change = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                change = mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return change;
    }

    public List<EmployeeChange> getAll() {
        String sql = "SELECT * FROM EmployeeChange";
        List<EmployeeChange> list = new ArrayList<>();

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

    public void insert(EmployeeChange change) {
        String sql = "INSERT INTO EmployeeChange " +
                "(employee_id, change_date, old_position_id, new_position_id, old_salary, new_salary) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, change.getEmployeeId());
            ps.setDate(2, Date.valueOf(change.getChangeDate()));

            if (change.getOldPositionId() != null) {
                ps.setInt(3, change.getOldPositionId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setInt(4, change.getNewPositionId());

            if (change.getOldSalary() != null) {
                ps.setBigDecimal(5, change.getOldSalary());
            } else {
                ps.setNull(5, Types.DECIMAL);
            }

            ps.setBigDecimal(6, change.getNewSalary());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeChange WHERE change_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private EmployeeChange mapResultSet(ResultSet rs) throws SQLException {
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
}

