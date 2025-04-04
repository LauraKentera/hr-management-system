package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final NationalityDAO nationalityDAO = new NationalityDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final PositionDAO positionDAO = new PositionDAO();

    public Employee getById(int id) {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee employee = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                employee = mapResultSetToEmployee(rs);
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving employee with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return employee;
    }

    public List<Employee> getAll() {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }

        } catch (SQLException e) {
            throw new DLException("Error fetching all employees", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, rs);
        }

        return employees;
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        int managerId = rs.getInt("manager_id");
        Employee manager = (managerId != 0) ? getById(managerId) : null;

        return new Employee(
            rs.getInt("id"),
            rs.getString("PIN"),
            rs.getString("last_name"),
            rs.getString("first_name"),
            rs.getDate("birth_date").toLocalDate(),
            rs.getDate("date_of_hire").toLocalDate(),
            rs.getDate("date_of_dismissal") != null ? rs.getDate("date_of_dismissal").toLocalDate() : null,
            rs.getString("phone_number"),
            rs.getString("email"),
            rs.getString("address"),
            rs.getString("gender"),
            nationalityDAO.getById(rs.getInt("nationality_id")),
            departmentDAO.getById(rs.getInt("department_id")),
            positionDAO.getById(rs.getInt("position_id")),
            rs.getString("employment_status"),
            rs.getString("emergency_contact_name"),
            rs.getString("emergency_contact_phone"),
            rs.getString("marital_status"),
            rs.getString("employment_type"),
            manager,
            rs.getString("tax_id"),
            rs.getString("bank_account_number")
        );
    }

    public void insert(Employee employee) {
        String sql = "INSERT INTO Employee (PIN, last_name, first_name, birth_date, date_of_hire, date_of_dismissal, phone_number, email, address, gender, nationality_id, department_id, position_id, employment_status, emergency_contact_name, emergency_contact_phone, marital_status, employment_type, manager_id, tax_id, bank_account_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getPIN());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getFirstName());
            ps.setDate(4, Date.valueOf(employee.getBirthDate()));
            ps.setDate(5, Date.valueOf(employee.getDateOfHire()));
            ps.setDate(6, employee.getDateOfDismissal() != null ? Date.valueOf(employee.getDateOfDismissal()) : null);
            ps.setString(7, employee.getPhoneNumber());
            ps.setString(8, employee.getEmail());
            ps.setString(9, employee.getAddress());
            ps.setString(10, employee.getGender());
            ps.setInt(11, employee.getNationality().getId());
            ps.setInt(12, employee.getDepartment().getId());
            ps.setInt(13, employee.getPosition().getId());
            ps.setString(14, employee.getEmploymentStatus());
            ps.setString(15, employee.getEmergencyContactName());
            ps.setString(16, employee.getEmergencyContactPhone());
            ps.setString(17, employee.getMaritalStatus());
            ps.setString(18, employee.getEmploymentType());
            ps.setObject(19, employee.getManager() != null ? employee.getManager().getId() : null);
            ps.setString(20, employee.getTaxId());
            ps.setString(21, employee.getBankAccountNumber());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error inserting employee", e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Employee WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting employee with ID " + id, e);
        } finally {
            DatabaseConnection.closeResources(conn, ps, null);
        }
    }
}
