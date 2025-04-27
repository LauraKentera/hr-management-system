package hrms.human_resource_system.model;

public class ContractAnnex {
    private int annexId;
    private int contractId;
    private String documentPath;
    private String description;

    // Constructor
    public ContractAnnex(int annexId, int contractId, String documentPath, String description) {
        this.annexId = annexId;
        this.contractId = contractId;
        this.documentPath = documentPath;
        this.description = description;
    }

    // Getters and Setters
    public int getAnnexId() {
        return annexId;
    }

    public void setAnnexId(int annexId) {
        this.annexId = annexId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
