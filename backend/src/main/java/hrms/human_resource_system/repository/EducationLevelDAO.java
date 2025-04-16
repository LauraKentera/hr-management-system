package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.EducationLevel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EducationLevelDAO {

    public EducationLevel getById(int id) {
        String sql = "SELECT * FROM EducationLevel WHERE education_level_id = ?";
        EducationLevel educationLevel = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                educationLevel = new EducationLevel(
                        rs.getInt("education_level_id"),
                        rs.getString("name"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("modification_date").toLocalDateTime(),
                        rs.getBoolean("is_active")
                );
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving education level with ID " + id, e);
        }

        return educationLevel;
    }

    public List<EducationLevel> getAll() {
        String sql = "SELECT * FROM EducationLevel";
        List<EducationLevel> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new EducationLevel(
                        rs.getInt("education_level_id"),
                        rs.getString("name"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("modification_date").toLocalDateTime(),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error fetching all education levels", e);
        }

        return list;
    }

    public void insert(EducationLevel educationLevel) {
        String sql = "INSERT INTO EducationLevel (name, user_id, modification_date, is_active) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, educationLevel.getName());
            ps.setInt(2, educationLevel.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(educationLevel.getModificationDate()));
            ps.setBoolean(4, educationLevel.isActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error inserting education level: " + educationLevel.getName(), e);
        }
    }

    public void update(int id, EducationLevel educationLevel) {
        String sql = "UPDATE EducationLevel SET name = ?, user_id = ?, modification_date = ?, is_active = ? WHERE education_level_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, educationLevel.getName());
            ps.setInt(2, educationLevel.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(educationLevel.getModificationDate()));
            ps.setBoolean(4, educationLevel.isActive());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error updating education level with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EducationLevel WHERE education_level_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error deleting education level with ID " + id, e);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM EducationLevel WHERE education_level_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new DLException("Error checking if education level exists with ID " + id, e);
        }
        return false;
    }
}
