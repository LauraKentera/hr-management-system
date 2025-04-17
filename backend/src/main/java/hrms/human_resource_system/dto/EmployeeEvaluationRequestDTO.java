package main.java.hrms.human_resource_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeEvaluationRequestDTO {

    private int evaluationId; // Added field
    private LocalDateTime evaluationDate; // Added field
    private int employeeId;
    private String evaluatorName;
    private String comments;
    private double score;
    private LocalDateTime entryDate; // Added field

    // Getter and Setter for evaluationId
    public int getEvaluationId() {
        return evaluationId;
    }

    // Getter and Setter for evaluationDate
    public LocalDate getEvaluationDate() {
        return LocalDate.from(evaluationDate);
    }

    // Getter and Setter for employeeId
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    // Getter and Setter for evaluatorName
    public String getEvaluatorName() {
        return evaluatorName;
    }

    // Getter and Setter for comments
    public String getComment() {
        return comments;
    }

    // Getter and Setter for score
    public double getScore() {
        return score;
    }

    // Getter and Setter for entryDate
    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public int getUserId() {
        return employeeId;
    }
}
