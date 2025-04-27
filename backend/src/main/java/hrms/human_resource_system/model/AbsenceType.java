package hrms.human_resource_system.model;

public class AbsenceType {
    private int absenceTypeId;
    private String name;
    private String code;
    private String description;
    private boolean isPaid;
    private boolean requiresApproval;
    private boolean isActive;

    public AbsenceType() {}

    public AbsenceType(int absenceTypeId, String name, String code, String description, boolean isPaid, boolean requiresApproval, boolean isActive) {
        this.absenceTypeId = absenceTypeId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.isPaid = isPaid;
        this.requiresApproval = requiresApproval;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getAbsenceTypeId() {
        return absenceTypeId;
    }

    public void setAbsenceTypeId(int absenceTypeId) {
        this.absenceTypeId = absenceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isRequiresApproval() {
        return requiresApproval;
    }

    public void setRequiresApproval(boolean requiresApproval) {
        this.requiresApproval = requiresApproval;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
