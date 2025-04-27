package hrms.human_resource_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BenefitItem {
    private int benefitItemId;
    private int benefitId;
    private Integer regionId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean allowCoefficient;
    private boolean useStandardAmount;
    private BigDecimal amount;
    private BigDecimal coefficient;

    public BenefitItem() {}

    public BenefitItem(int benefitItemId, int benefitId, Integer regionId, LocalDate fromDate, LocalDate toDate,
                       boolean allowCoefficient, boolean useStandardAmount,
                       BigDecimal amount, BigDecimal coefficient) {
        this.benefitItemId = benefitItemId;
        this.benefitId = benefitId;
        this.regionId = regionId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.allowCoefficient = allowCoefficient;
        this.useStandardAmount = useStandardAmount;
        this.amount = amount;
        this.coefficient = coefficient;
    }

    public int getBenefitItemId() { return benefitItemId; }
    public void setBenefitItemId(int benefitItemId) { this.benefitItemId = benefitItemId; }

    public int getBenefitId() { return benefitId; }
    public void setBenefitId(int benefitId) { this.benefitId = benefitId; }

    public Integer getRegionId() { return regionId; }
    public void setRegionId(Integer regionId) { this.regionId = regionId; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public boolean isAllowCoefficient() { return allowCoefficient; }
    public void setAllowCoefficient(boolean allowCoefficient) { this.allowCoefficient = allowCoefficient; }

    public boolean isUseStandardAmount() { return useStandardAmount; }
    public void setUseStandardAmount(boolean useStandardAmount) { this.useStandardAmount = useStandardAmount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getCoefficient() { return coefficient; }
    public void setCoefficient(BigDecimal coefficient) { this.coefficient = coefficient; }
}
