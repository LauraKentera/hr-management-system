package main.java.hrms.human_resource_system.model;

import java.time.LocalDateTime;

public class EducationLevel {
    private int educationLevelId;
    private String name;
    private int userId;
    private LocalDateTime modificationDate;
    private boolean isActive;

    public EducationLevel() {}

    public EducationLevel(int educationLevelId, String name, int userId, LocalDateTime modificationDate, boolean isActive) {
        this.educationLevelId = educationLevelId;
        this.name = name;
        this.userId = userId;
        this.modificationDate = modificationDate;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getEducationLevelId() {
        return educationLevelId;
    }

    public void setEducationLevelId(int educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
