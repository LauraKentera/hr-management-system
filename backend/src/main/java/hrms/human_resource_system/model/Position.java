package hrms.human_resource_system.model;

public class Position {
    private int positionId;
    private Position parentPosition;
    private String name;
    private String shortName;
    private int educationLevelId;
    private String benefits;
    private boolean requiresLicensing;
    private boolean isActive;

    public Position() {}

    public Position(int positionId, Position parentPosition, String name, String shortName, int educationLevelId, String benefits, boolean requiresLicensing, boolean isActive) {
        this.positionId = positionId;
        this.parentPosition = parentPosition;
        this.name = name;
        this.shortName = shortName;
        this.educationLevelId = educationLevelId;
        this.benefits = benefits;
        this.requiresLicensing = requiresLicensing;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Position getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(Position parentPosition) {
        this.parentPosition = parentPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getEducationLevelId() {
        return educationLevelId;
    }

    public void setEducationLevelId(int educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public boolean isRequiresLicensing() {
        return requiresLicensing;
    }

    public void setRequiresLicensing(boolean requiresLicensing) {
        this.requiresLicensing = requiresLicensing;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
