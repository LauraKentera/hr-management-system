package main.java.hrms.human_resource_system.model;

public class DisabilityCategory {
    private int disabilityCategoryId;
    private String name;
    private String description;
    private String legalCode;
    private boolean isActive;

    public DisabilityCategory() {}

    public DisabilityCategory(int disabilityCategoryId, String name, String description, String legalCode, boolean isActive) {
        this.disabilityCategoryId = disabilityCategoryId;
        this.name = name;
        this.description = description;
        this.legalCode = legalCode;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getDisabilityCategoryId() {
        return disabilityCategoryId;
    }

    public void setDisabilityCategoryId(int disabilityCategoryId) {
        this.disabilityCategoryId = disabilityCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLegalCode() {
        return legalCode;
    }

    public void setLegalCode(String legalCode) {
        this.legalCode = legalCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
