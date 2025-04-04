import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    // Create a new Department record
    public void createDepartment(Department dept) throws SQLException {
        String sql = "INSERT INTO Department (DepartmentName, ManagerName, Location, Description, UserID) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setString(1, dept.getDepartmentName());
            ps.setString(2, dept.getManagerName());
            ps.setString(3, dept.getLocation());
            ps.setString(4, dept.getDescription());
            ps.setInt(5, dept.getUserId());
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dept.setDepartmentId(rs.getInt(1));
                }
            }
        }
    }
    
    // Retrieve a Department by its ID
    public Department getDepartmentById(int id) throws SQLException {
        String sql = "SELECT * FROM Department WHERE DepartmentID = ?";
        Department dept = null;
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dept = new Department();
                    dept.setDepartmentId(rs.getInt("DepartmentID"));
                    dept.setDepartmentName(rs.getString("DepartmentName"));
                    dept.setManagerName(rs.getString("ManagerName"));
                    dept.setLocation(rs.getString("Location"));
                    dept.setDescription(rs.getString("Description"));
                    dept.setUserId(rs.getInt("UserID"));
                    dept.setEntryDate(rs.getTimestamp("EntryDate"));
                }
            }
        }
        return dept;
    }
    
    // Retrieve all Departments
    public List<Department> getAllDepartments() throws SQLException {
        String sql = "SELECT * FROM Department";
        List<Department> departments = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Department dept = new Department();
                dept.setDepartmentId(rs.getInt("DepartmentID"));
                dept.setDepartmentName(rs.getString("DepartmentName"));
                dept.setManagerName(rs.getString("ManagerName"));
                dept.setLocation(rs.getString("Location"));
                dept.setDescription(rs.getString("Description"));
                dept.setUserId(rs.getInt("UserID"));
                dept.setEntryDate(rs.getTimestamp("EntryDate"));
                departments.add(dept);
            }
        }
        return departments;
    }
    
    

    // Update an existing Department
    public void updateDepartment(Department dept) throws SQLException {
        String sql = "UPDATE Department SET DepartmentName = ?, ManagerName = ?, Location = ?, Description = ?, UserID = ? WHERE DepartmentID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, dept.getDepartmentName());
            ps.setString(2, dept.getManagerName());
            ps.setString(3, dept.getLocation());
            ps.setString(4, dept.getDescription());
            ps.setInt(5, dept.getUserId());
            ps.setInt(6, dept.getDepartmentId());
            ps.executeUpdate();
        }
    }
    
    // Delete a Department by its ID
    public void deleteDepartment(int id) throws SQLException {
        String sql = "DELETE FROM Department WHERE DepartmentID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
