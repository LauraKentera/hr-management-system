package hrms.human_resource_system.service;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.repository.DepartmentDAO;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Supplier;

@Service
public class EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final DepartmentDAO departmentDAO;
    private final RoleDAO roleDAO;

    // Constructor injection for DAOs
    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO, DepartmentDAO departmentDAO, RoleDAO roleDAO) {
        this.employeeDAO = employeeDAO;
        this.departmentDAO = departmentDAO;
        this.roleDAO = roleDAO;
    }

    // Retirement eligibility constants
    private static final int RETIREMENT_AGE = 65;
    private static final int MIN_SERVICE_YEARS = 40;

    public boolean isEligibleForRetirement(int employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
        return calculateRetirementEligibility(employee);
    }

    public boolean isEligibleForRetirement(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        return calculateRetirementEligibility(employee);
    }

    private boolean calculateRetirementEligibility(Employee employee) {
        LocalDate today = LocalDate.now();

        // Calculate age
        int age = Period.between(employee.getBirthDate(), today).getYears();

        // Calculate years of service
        int serviceYears = 0;
        if (employee.getDateOfHire() != null) {
            serviceYears = Period.between(employee.getDateOfHire(), today).getYears();
            // Adjust for partial years
            if (today.getMonthValue() < employee.getDateOfHire().getMonthValue() ||
                    (today.getMonthValue() == employee.getDateOfHire().getMonthValue() &&
                            today.getDayOfMonth() < employee.getDateOfHire().getDayOfMonth())) {
                serviceYears--;
            }
        }

        return age >= RETIREMENT_AGE || serviceYears >= MIN_SERVICE_YEARS;
    }

    public void validateEmployee(Employee employee) {
        if (employee.getPIN() == null || employee.getPIN().isEmpty()) {
            throw new IllegalArgumentException("PIN is required");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (employee.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required");
        }
        if (employee.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
        if (employee.getDepartment() == null || !departmentDAO.existsById(employee.getDepartment().getDepartmentId())) {
            throw new IllegalArgumentException("Invalid department ID");
        }
        if (employee.getPosition() == null || !roleDAO.existsById(employee.getPosition().getPositionId())) {
            throw new IllegalArgumentException("Invalid role ID");
        }
        if (employee.getDateOfHire() != null && employee.getDateOfDismissal() != null &&
                employee.getDateOfHire().isAfter(employee.getDateOfDismissal())) {
            throw new IllegalArgumentException("Date of hire cannot be after the date of dismissal");
        }
    }

    // Add a new employee
    public Employee createEmployee(Employee employee, int performedBy) {
        validateEmployee(employee);  // Validate the employee data before adding
        return employeeDAO.insert(employee, performedBy);  // Pass the performedBy value
    }

    // Update an existing employee by ID
    public Employee updateEmployee(int id, Employee employee, int performedBy) {
        validateEmployee(employee);  // Validate the employee data before updating
        Employee existingEmployee = getEmployeeById(id);
        if (existingEmployee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employee.setId(id);  // Set the employee ID to ensure correct update
        return employeeDAO.update(id, employee, performedBy);  // Pass the performedBy value
    }

    // Delete an employee by ID
    public void deleteEmployee(int id, int performedBy) {
        if (getEmployeeById(id) == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employeeDAO.delete(id, performedBy);  // Pass the performedBy value
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();  // Get all employees from the database
    }

    // Retrieve a specific employee by ID
    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);  // Get employee by ID
    }

    public List<Employee> getEmployeesEligibleForRetirement() {
        List<Employee> allEmployees = getAllEmployees();
        return allEmployees.stream()
                .filter(this::isEligibleForRetirement)
                .toList();
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (DLException e) {
            throw e;
        } catch (Exception e) {
            throw new DLException("Unexpected error: " + e.getMessage(), e);
        }
    }


    public Employee getByUserId(int userId) {
        return wrap(() -> employeeDAO.getByUserId(userId));
    }

    // Method to fetch employee name by ID
    public String getEmployeeNameById(int employeeId) {
        Employee employee = employeeDAO.getById(employeeId);
        if (employee != null) {
            return employee.getFirstName() + " " + employee.getLastName(); // Full Name
        }
        return null;  // Or throw an exception if needed
    }

}
