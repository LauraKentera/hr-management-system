package hrms.human_resource_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeEvaluationResponseDTO {
    private int id;
    private int employeeId;
    private String evaluatorName;
    private String comments;
    private double score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEvaluatorName() {
        return evaluatorName;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String setComment(String comment) {
        return comments;
    }

    public Integer setEvaluationId(Integer evaluationId) {
        return evaluationId;
    }

    public String setEvaluationDate(LocalDate evaluationDate) {
        return evaluationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public int setUserId(int userId) {
        return userId;
    }

    public LocalDateTime setEntryDate(LocalDateTime entryDate) {
        return entryDate;
    }
}
