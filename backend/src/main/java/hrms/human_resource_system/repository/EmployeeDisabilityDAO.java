package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.EmployeeDisability;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDisabilityDAO {

    // Get EmployeeDisability by ID
    public EmployeeDisability getById(int id) {
        String sql = "SELECT * FROM EmployeeDisability WHERE employee_disability_id = ?";
        EmployeeDisability disability = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                disability = mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disability;
    }

    // Get all EmployeeDisability records
    public List<EmployeeDisability> getAll() {
        String sql = "SELECT * FROM EmployeeDisability";
        List<EmployeeDisability> list = new ArrayList<>();

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

    // Insert a new EmployeeDisability
    public void insert(EmployeeDisability disability) {
        String sql = "INSERT INTO EmployeeDisability " +
                "(employee_id, disability_category_id, official_code, from_date, to_date, description, percentage, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, disability.getEmployeeId());
            ps.setInt(2, disability.getDisabilityCategoryId());
            ps.setString(3, disability.getOfficialCode());
            ps.setDate(4, Date.valueOf(disability.getFromDate()));
            if (disability.getToDate() != null) {
                ps.setDate(5, Date.valueOf(disability.getToDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, disability.getDescription());
            if (disability.getPercentage() != null) {
                ps.setInt(7, disability.getPercentage());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            ps.setBoolean(8, disability.isActive());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing EmployeeDisability record
    public void update(int id, EmployeeDisability disability) {
        String sql = "UPDATE EmployeeDisability SET " +
                "employee_id = ?, " +
                "disability_category_id = ?, " +
                "official_code = ?, " +
                "from_date = ?, " +
                "to_date = ?, " +
                "description = ?, " +
                "percentage = ?, " +
                "is_active = ? " +
                "WHERE employee_disability_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, disability.getEmployeeId());
            ps.setInt(2, disability.getDisabilityCategoryId());
            ps.setString(3, disability.getOfficialCode());
            ps.setDate(4, Date.valueOf(disability.getFromDate()));
            if (disability.getToDate() != null) {
                ps.setDate(5, Date.valueOf(disability.getToDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, disability.getDescription());
            if (disability.getPercentage() != null) {
                ps.setInt(7, disability.getPercentage());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            ps.setBoolean(8, disability.isActive());
            ps.setInt(9, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating EmployeeDisability failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete EmployeeDisability by ID
    public void delete(int id) {
        String sql = "DELETE FROM EmployeeDisability WHERE employee_disability_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Map ResultSet to EmployeeDisability object
    private EmployeeDisability mapResultSet(ResultSet rs) throws SQLException {
        LocalDate fromDate = rs.getDate("from_date").toLocalDate();
        LocalDate toDate = rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null;

        return new EmployeeDisability(
                rs.getInt("employee_disability_id"),
                rs.getInt("employee_id"),
                rs.getInt("disability_category_id"),
                rs.getString("official_code"),
                fromDate,
                toDate,
                rs.getString("description"),
                rs.getInt("percentage"),
                rs.getBoolean("is_active")
        );
    }
}
