package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            e.printStackTrace(); // Use DLException in your setup
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
            e.printStackTrace();
        }

        return departments;
    }

    public void insert(Department department) {
        String sql = "INSERT INTO Department (name, manager_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getName());
            ps.setObject(2, department.getManagerId(), Types.INTEGER);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Department WHERE department_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

