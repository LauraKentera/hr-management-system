package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.Nationality;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NationalityDAO {

    public List<Nationality> getAll() {
        String sql = "SELECT * FROM Nationality";
        List<Nationality> nationalities = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Nationality nationality = new Nationality(
                        rs.getInt("nationality_id"),
                        rs.getString("name"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("modification_date") != null ?
                                rs.getTimestamp("modification_date").toLocalDateTime() : null,
                        rs.getBoolean("is_active")
                );
                nationalities.add(nationality);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nationalities;
    }

    public Nationality getById(Integer nationalityId) {
        String sql = "SELECT * FROM Nationality WHERE nationality_id = ?";
        Nationality nationality = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nationalityId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nationality = new Nationality(
                            rs.getInt("nationality_id"),
                            rs.getString("name"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("modification_date") != null ?
                                    rs.getTimestamp("modification_date").toLocalDateTime() : null,
                            rs.getBoolean("is_active")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nationality;
    }

    public void insert(Nationality nationality) {
        String sql = "INSERT INTO Nationality (name, user_id, modification_date, is_active) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nationality.getName());
            ps.setObject(2, nationality.getUserId(), Types.INTEGER);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, nationality.getIsActive());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    nationality.setNationalityId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Nationality nationality) {
        String sql = "UPDATE Nationality SET name = ?, user_id = ?, modification_date = ?, is_active = ? " +
                "WHERE nationality_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nationality.getName());
            ps.setObject(2, nationality.getUserId(), Types.INTEGER);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, nationality.getIsActive());
            ps.setInt(5, nationality.getNationalityId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer nationalityId) {
        String sql = "DELETE FROM Nationality WHERE nationality_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nationalityId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
