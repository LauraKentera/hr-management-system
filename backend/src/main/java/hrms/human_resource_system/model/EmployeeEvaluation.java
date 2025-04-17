package main.java.hrms.human_resource_system.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeEvaluation {

    private int employeeEvaluationId;
    private Integer evaluationId;
    private LocalDate evaluationDate;
    private String comment;
    private Double score;
    private int userId;
    private LocalDateTime entryDate;

    // Constructor
    public EmployeeEvaluation(int employeeEvaluationId, Integer evaluationId, LocalDate evaluationDate,
                              String comment, Double score, int userId, LocalDateTime entryDate) {
        this.employeeEvaluationId = employeeEvaluationId;
        this.evaluationId = evaluationId;
        this.evaluationDate = evaluationDate;
        this.comment = comment;
        this.score = score;
        this.userId = userId;
        this.entryDate = entryDate;
    }

    public EmployeeEvaluation() {

    }

    // Getters and Setters
    public int getEmployeeEvaluationId() {
        return employeeEvaluationId;
    }

    public void setEmployeeEvaluationId(int employeeEvaluationId) {
        this.employeeEvaluationId = employeeEvaluationId;
    }

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
