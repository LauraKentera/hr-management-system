package main.java.hrms.human_resource_system.model;

import java.time.LocalDateTime;

public class Nationality {
    private Integer nationalityId;
    private String name;
    private Integer userId;
    private LocalDateTime modificationDate;
    private Boolean isActive;

    public Nationality() {
        this.isActive = true;
    }

    public Nationality(Integer nationalityId, String name, Integer userId,
            LocalDateTime modificationDate, Boolean isActive) {
        this.nationalityId = nationalityId;
        this.name = name;
        this.userId = userId;
        this.modificationDate = modificationDate;
        this.isActive = isActive;
    }

    public Integer getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Integer nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Nationality{" +
                "nationalityId=" + nationalityId +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}