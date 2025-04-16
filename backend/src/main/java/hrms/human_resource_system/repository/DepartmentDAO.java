package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Department;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentDAO {

    public Department getById(int id) {
        String sql = "SELECT * FROM Department WHERE department_id = ?";
        Department department = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                department = new Department(
                        rs.getInt("department_id"),
                        rs.getString("name"),
                        rs.getInt("manager_id")
                );
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving department with ID " + id, e);
        }

        return department;
    }

    public List<Department> getAll() {
        String sql = "SELECT * FROM Department";
        List<Department> departments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(new Department(
                        rs.getInt("department_id"),
                        rs.getString("name"),
                        rs.getInt("manager_id")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error fetching all departments", e);
        }

        return departments;
    }

    public void insert(Department department) {
        String sql = "INSERT INTO Department (name, manager_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, department.getName());
            ps.setObject(2, department.getManagerId(), Types.INTEGER);
            ps.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setDepartmentId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DLException("Error inserting department: " + department.getName(), e);
        }
    }

    public void update(int id, Department department, int performedBy) {
        String sql = "UPDATE Department SET name = ?, manager_id = ? WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getName());
            ps.setObject(2, department.getManagerId(), Types.INTEGER);
            ps.setInt(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error updating department with ID " + id, e);
        }
    }

    public void delete(int id, int performedBy) {
        String sql = "DELETE FROM Department WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting department with ID " + id, e);
        }
    }
}
