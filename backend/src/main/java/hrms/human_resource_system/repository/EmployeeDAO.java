package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.util.AuditLogger;
import hrms.human_resource_system.model.Nationality;
import hrms.human_resource_system.model.Department;
import hrms.human_resource_system.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NationalityDAO nationalityDAO;
    private final DepartmentDAO departmentDAO;
    private final PositionDAO positionDAO;
    private final AuditLogger auditLogger;

    @Autowired
    public EmployeeDAO(JdbcTemplate jdbcTemplate,
                       NationalityDAO nationalityDAO,
                       DepartmentDAO departmentDAO,
                       PositionDAO positionDAO,
                       AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.nationalityDAO = nationalityDAO;
        this.departmentDAO = departmentDAO;
        this.positionDAO = positionDAO;
        this.auditLogger = auditLogger;
    }

    public boolean existsById(int employeeId) {
        String sql = "SELECT COUNT(*) FROM Employee WHERE id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, employeeId);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DLException("Error checking if employee exists with ID " + employeeId, e);
        }
    }

    public Employee getById(int id) {
        String sql = "SELECT * FROM Employee WHERE id = ? AND is_deleted = FALSE";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapResultSetToEmployee, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving employee with ID " + id, e);
        }
    }
    

    public List<Employee> getAll() {
        String sql = "SELECT * FROM Employee WHERE is_deleted = FALSE";
        try {
            return jdbcTemplate.query(sql, this::mapResultSetToEmployee);
        } catch (Exception e) {
            throw new DLException("Error fetching all employees", e);
        }
    }
    

    public Employee insert(Employee employee, int performedBy) {
        String sql = """
                    INSERT INTO Employee 
                    (PIN, last_name, first_name, birth_date, date_of_hire, date_of_dismissal, phone_number, email, address, 
                     gender, nationality_id, department_id, position_id, employment_status, emergency_contact_name, 
                     emergency_contact_phone, marital_status, employment_type, manager_id, tax_id, bank_account_number, user_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;


        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, employee.getPIN());
                ps.setString(2, employee.getLastName());
                ps.setString(3, employee.getFirstName());
                ps.setDate(4, Date.valueOf(employee.getBirthDate()));
                ps.setDate(5, Date.valueOf(employee.getDateOfHire()));
                ps.setObject(6, employee.getDateOfDismissal() != null ? Date.valueOf(employee.getDateOfDismissal()) : null);
                ps.setString(7, employee.getPhoneNumber());
                ps.setString(8, employee.getEmail());
                ps.setString(9, employee.getAddress());
                ps.setString(10, employee.getGender());
                ps.setInt(11, employee.getNationality().getNationalityId());
                ps.setInt(12, employee.getDepartment().getDepartmentId());
                ps.setInt(13, employee.getPosition().getPositionId());
                ps.setString(14, employee.getEmploymentStatus());
                ps.setString(15, employee.getEmergencyContactName());
                ps.setString(16, employee.getEmergencyContactPhone());
                ps.setString(17, employee.getMaritalStatus());
                ps.setString(18, employee.getEmploymentType());
                if (employee.getManager() != null)
                    ps.setInt(19, employee.getManager().getId());
                else
                    ps.setNull(19, Types.INTEGER);
                ps.setString(20, employee.getTaxId());
                ps.setString(21, employee.getBankAccountNumber());
                ps.setInt(22, employee.getUserId());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                employee.setId(keyHolder.getKey().intValue());
            }

            auditLogger.logChange("Employee", employee.getId(), "CREATE", performedBy, null, employee);

            return employee;

        } catch (Exception e) {
            throw new DLException("Error inserting employee", e);
        }
    }

    public Employee update(int id, Employee employee, int performedBy) {
        String sql = """
                    UPDATE Employee SET 
                    PIN = ?, last_name = ?, first_name = ?, birth_date = ?, date_of_hire = ?, date_of_dismissal = ?, 
                    phone_number = ?, email = ?, address = ?, gender = ?, nationality_id = ?, department_id = ?, 
                    position_id = ?, employment_status = ?, emergency_contact_name = ?, emergency_contact_phone = ?, 
                    marital_status = ?, employment_type = ?, manager_id = ?, tax_id = ?, bank_account_number = ? 
                    WHERE id = ?
                """;

        Employee oldEmployee = getById(id);

        try {
            jdbcTemplate.update(sql,
                    employee.getPIN(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    Date.valueOf(employee.getBirthDate()),
                    Date.valueOf(employee.getDateOfHire()),
                    employee.getDateOfDismissal() != null ? Date.valueOf(employee.getDateOfDismissal()) : null,
                    employee.getPhoneNumber(),
                    employee.getEmail(),
                    employee.getAddress(),
                    employee.getGender(),
                    employee.getNationality().getNationalityId(),
                    employee.getDepartment().getDepartmentId(),
                    employee.getPosition().getPositionId(),
                    employee.getEmploymentStatus(),
                    employee.getEmergencyContactName(),
                    employee.getEmergencyContactPhone(),
                    employee.getMaritalStatus(),
                    employee.getEmploymentType(),
                    employee.getManager() != null ? employee.getManager().getId() : null,
                    employee.getTaxId(),
                    employee.getBankAccountNumber(),
                    id
            );

            auditLogger.logChange("Employee", id, "UPDATE", performedBy, oldEmployee, employee);
            return employee;

        } catch (Exception e) {
            throw new DLException("Error updating employee with ID " + id, e);
        }
    }

    public void delete(int id, int performedBy) {
        String sql = "UPDATE Employee SET is_deleted = TRUE WHERE id = ?";
        Employee oldEmployee = getById(id);
    
        try {
            jdbcTemplate.update(sql, id);
            auditLogger.logChange("Employee", id, "DELETE", performedBy, oldEmployee, null);
        } catch (Exception e) {
            throw new DLException("Error soft-deleting employee with ID " + id, e);
        }
    }
    
    

    private Employee mapResultSetToEmployee(ResultSet rs, int rowNum) throws SQLException {
        int nationalityId = rs.getInt("nationality_id");
        int departmentId = rs.getInt("department_id");
        int positionId = rs.getInt("position_id");
        int managerId = rs.getInt("manager_id");

        return new Employee(
                rs.getInt("id"),
                rs.getString("PIN"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getDate("date_of_hire").toLocalDate(),
                rs.getDate("date_of_dismissal") != null ? rs.getDate("date_of_dismissal").toLocalDate() : null,
                rs.getString("phone_number"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("gender"),
                nationalityDAO.getById(nationalityId),
                departmentDAO.getById(departmentId),
                positionDAO.getById(positionId),
                rs.getString("employment_status"),
                rs.getString("emergency_contact_name"),
                rs.getString("emergency_contact_phone"),
                rs.getString("marital_status"),
                rs.getString("employment_type"),
                (managerId != 0 ? getById(managerId) : null),
                rs.getString("tax_id"),
                rs.getString("bank_account_number")
        );
    }

    public Employee getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM Employee WHERE user_id = ?";
            return jdbcTemplate.queryForObject(sql, this::mapRow, userId);
        } catch (Exception e) {
            throw new DLException("Error retrieving employee by user ID: " + userId, e);
        }
    }
    public boolean existsByDepartment(int departmentId) {
        String sql = "SELECT COUNT(*) FROM Employee WHERE department_id = ? AND is_deleted = FALSE";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, departmentId);
        return count != null && count > 0;
    }
    

    private Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapResultSetToEmployee(rs, rowNum);
    }

}
