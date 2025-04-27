package hrms.human_resource_system.dto;

public class PaginationDTO {
    private int pageNumber;         // e.g., 0-based index
    private int pageSize;           // number of records per page
    private String sortField;       // e.g., "lastName", "hireDate"
    private String sortDirection;   // "asc" or "desc"

    // Constructors
    public PaginationDTO() {}

    public PaginationDTO(int pageNumber, int pageSize, String sortField, String sortDirection) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    // Getters and Setters
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
