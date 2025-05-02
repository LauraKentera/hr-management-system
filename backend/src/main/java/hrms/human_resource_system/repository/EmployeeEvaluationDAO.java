package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmployeeEvaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class EmployeeEvaluationDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeEvaluationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EmployeeEvaluation mapRow(ResultSet rs, int rowNum) throws SQLException {
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

    public EmployeeEvaluation getById(int id) {
        String sql = "SELECT * FROM EmployeeEvaluation WHERE employee_evaluation_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving employee evaluation with ID " + id, e);
        }
    }

    public List<EmployeeEvaluation> getAll() {
        String sql = "SELECT * FROM EmployeeEvaluation";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching all employee evaluations", e);
        }
    }

    public void insert(EmployeeEvaluation evaluation, int performedBy) {
        String sql = """
            INSERT INTO EmployeeEvaluation 
            (evaluation_id, evaluation_date, comment, score, user_id, entry_date, performed_by)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            jdbcTemplate.update(sql,
                    evaluation.getEvaluationId(),
                    Date.valueOf(evaluation.getEvaluationDate()),
                    evaluation.getComment(),
                    evaluation.getScore(),
                    evaluation.getUserId(),
                    Timestamp.valueOf(evaluation.getEntryDate()),
                    performedBy
            );
        } catch (Exception e) {
            throw new DLException("Error inserting employee evaluation", e);
        }
    }

    public void update(int id, EmployeeEvaluation evaluation, int performedBy) {
        String sql = """
            UPDATE EmployeeEvaluation SET 
            evaluation_id = ?, evaluation_date = ?, comment = ?, score = ?, 
            user_id = ?, entry_date = ?, performed_by = ?
            WHERE employee_evaluation_id = ?
        """;

        try {
            jdbcTemplate.update(sql,
                    evaluation.getEvaluationId(),
                    Date.valueOf(evaluation.getEvaluationDate()),
                    evaluation.getComment(),
                    evaluation.getScore(),
                    evaluation.getUserId(),
                    Timestamp.valueOf(evaluation.getEntryDate()),
                    performedBy,
                    id
            );
        } catch (Exception e) {
            throw new DLException("Error updating employee evaluation with ID " + id, e);
        }
    }

    public void delete(int id, int performedBy) {
        String sql = "DELETE FROM EmployeeEvaluation WHERE employee_evaluation_id = ?";
        try {
            jdbcTemplate.update(sql, id);
            // Optionally log performedBy here
        } catch (Exception e) {
            throw new DLException("Error deleting employee evaluation with ID " + id, e);
        }
    }
}
