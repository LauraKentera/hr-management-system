package main.java.hrms.human_resource_system.dto;

public class EmployeeEvaluationRequestDTO {
    private int employeeId;
    private String evaluatorName;
    private String comments;
    private double score;

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
}
