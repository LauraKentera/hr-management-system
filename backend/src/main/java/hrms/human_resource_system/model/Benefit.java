package main.java.hrms.human_resource_system.model;

public class Benefit {
    private int benefitId;
    private String name;
    private String description;
    private boolean isTaxable;
    private boolean isActive;

    public Benefit() {}

    public Benefit(int benefitId, String name, String description, boolean isTaxable, boolean isActive) {
        this.benefitId = benefitId;
        this.name = name;
        this.description = description;
        this.isTaxable = isTaxable;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getBenefitId() {
        return benefitId;
    }

    public void setBenefitId(int benefitId) {
        this.benefitId = benefitId;
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

    public boolean isTaxable() {
        return isTaxable;
    }

    public void setTaxable(boolean taxable) {
        isTaxable = taxable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
