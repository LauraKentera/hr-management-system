package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.EmployeeEvaluation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeEvaluationDAO {

    public EmployeeEvaluation getById(int id) {
        String sql = "SELECT * FROM EmployeeEvaluation WHERE employee_evaluation_id = ?";
        EmployeeEvaluation evaluation = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                evaluation = mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Or throw DLException
        }

        return evaluation;
    }

    public List<EmployeeEvaluation> getAll() {
        String sql = "SELECT * FROM EmployeeEvaluation";
        List<EmployeeEvaluation> list = new ArrayList<>();

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

    public void insert(EmployeeEvaluation eval) {
        String sql = "INSERT INTO EmployeeEvaluation (evaluation_id, evaluation_date, comment, score, user_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (eval.getEvaluationId() != null)
                ps.setInt(1, eval.getEvaluationId());
            else
                ps.setNull(1, Types.INTEGER);

            ps.setDate(2, Date.valueOf(eval.getEvaluationDate()));
            ps.setString(3, eval.getComment());
            ps.setDouble(4, eval.getScore());
            ps.setInt(5, eval.getUserId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeEvaluation WHERE employee_evaluation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private EmployeeEvaluation mapResultSet(ResultSet rs) throws SQLException {
        return new EmployeeEvaluation(
                rs.getInt("employee_evaluation_id"),
                rs.getInt("evaluation_id") == 0 ? null : rs.getInt("evaluation_id"),
                rs.getDate("evaluation_date") != null ? rs.getDate("evaluation_date").toLocalDate() : null,
                rs.getString("comment"),
                rs.getDouble("score"),
                rs.getInt("user_id"),
                rs.getTimestamp("entry_date") != null ? rs.getTimestamp("entry_date").toLocalDateTime() : null
        );
    }
}
