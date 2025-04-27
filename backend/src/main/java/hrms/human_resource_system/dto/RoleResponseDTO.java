package hrms.human_resource_system.dto;

public class RoleResponseDTO {

    private int id;
    private String name;

    // Constructor that accepts id and name
    public RoleResponseDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
