package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Nationality;
import main.java.hrms.human_resource_system.util.AuditLogger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NationalityDAO {

    public Nationality getById(int id) {
        String sql = "SELECT * FROM Nationality WHERE nationality_id = ?";
        Nationality nationality = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nationality = new Nationality(
                        rs.getInt("nationality_id"),
                        rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving nationality with ID " + id, e);
        }

        return nationality;
    }

    public List<Nationality> getAll() {
        String sql = "SELECT * FROM Nationality";
        List<Nationality> nationalities = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nationalities.add(new Nationality(
                    rs.getInt("nationality_id"),
                    rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error fetching all nationalities", e);
        }

        return nationalities;
    }

    public void insert(Nationality nationality, int performedBy) {
        String sql = "INSERT INTO Nationality (name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nationality.getName());
            ps.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    nationality.setNationalityId(generatedKeys.getInt(1));
                }
            }

            // Log the change
            AuditLogger.logChange("Nationality", nationality.getNationalityId(), "INSERT", performedBy, null, nationality);

        } catch (SQLException e) {
            throw new DLException("Error inserting nationality: " + nationality.getName(), e);
        }
    }

    public void delete(int id, int performedBy) {
        String sql = "DELETE FROM Nationality WHERE nationality_id = ?";
        Nationality oldNationality = getById(id); // Fetch old data for logging

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            // Log the change
            AuditLogger.logChange("Nationality", id, "DELETE", performedBy, oldNationality, null);

        } catch (SQLException e) {
            throw new DLException("Error deleting nationality with ID " + id, e);
        }
    }

    public void update(Nationality nationality, int performedBy) {
        String sql = "UPDATE Nationality SET name = ? WHERE nationality_id = ?";
        Nationality oldNationality = getById(nationality.getNationalityId()); // Fetch old data for logging

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nationality.getName());
            ps.setInt(2, nationality.getNationalityId());
            ps.executeUpdate();

            // Log the change
            AuditLogger.logChange("Nationality", nationality.getNationalityId(), "UPDATE", performedBy, oldNationality, nationality);

        } catch (SQLException e) {
            throw new DLException("Error updating nationality with ID " + nationality.getNationalityId(), e);
        }
    }
}
