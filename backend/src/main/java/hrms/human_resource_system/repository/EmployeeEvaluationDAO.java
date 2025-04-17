package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
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
            throw new DLException("Error retrieving employee evaluation with ID " + id, e);
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
            throw new DLException("Error fetching all employee evaluations", e);
        }

        return list;
    }

    public void insert(EmployeeEvaluation evaluation, int performedBy) {
        String sql = "INSERT INTO EmployeeEvaluation (evaluation_id, evaluation_date, comment, score, user_id, entry_date, performed_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, evaluation.getEvaluationId());
            ps.setDate(2, Date.valueOf(evaluation.getEvaluationDate()));
            ps.setString(3, evaluation.getComment());
            ps.setDouble(4, evaluation.getScore());
            ps.setInt(5, evaluation.getUserId());
            ps.setTimestamp(6, Timestamp.valueOf(evaluation.getEntryDate()));
            ps.setInt(7, performedBy);  // Add performedBy here

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error inserting employee evaluation", e);
        }
    }

    public void update(int id, EmployeeEvaluation evaluation, int performedBy) {
        String sql = "UPDATE EmployeeEvaluation SET evaluation_id = ?, evaluation_date = ?, comment = ?, score = ?, user_id = ?, entry_date = ?, performed_by = ? WHERE employee_evaluation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, evaluation.getEvaluationId());
            ps.setDate(2, Date.valueOf(evaluation.getEvaluationDate()));
            ps.setString(3, evaluation.getComment());
            ps.setDouble(4, evaluation.getScore());
            ps.setInt(5, evaluation.getUserId());
            ps.setTimestamp(6, Timestamp.valueOf(evaluation.getEntryDate()));
            ps.setInt(7, performedBy);  // Add performedBy here
            ps.setInt(8, id);  // Ensure to update the record by ID

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error updating employee evaluation with ID " + id, e);
        }
    }


    public void delete(int id, int performedBy) {
        String sql = "DELETE FROM EmployeeEvaluation WHERE employee_evaluation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            // You may also log who performed the delete operation (optional)
        } catch (SQLException e) {
            throw new DLException("Error deleting employee evaluation with ID " + id, e);
        }
    }

    private EmployeeEvaluation mapResultSet(ResultSet rs) throws SQLException {
        return new EmployeeEvaluation(
                rs.getInt("employee_evaluation_id"),
                rs.getInt("evaluation_id"),
                rs.getDate("evaluation_date").toLocalDate(),
                rs.getString("comment"),
                rs.getDouble("score"),
                rs.getInt("user_id"),
                rs.getTimestamp("entry_date").toLocalDateTime()
        );
    }
}
