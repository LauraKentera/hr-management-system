package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.repository.DepartmentDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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
    public void addEmployee(Employee employee) {
        validateEmployee(employee);
        return employeeDAO.insert(employee);
    }

    // Update an existing employee by ID
    public void updateEmployee(int id, Employee employee) {
        validateEmployee(employee);
        if (getEmployeeById(id) == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employee.setEmployeeId(id);
        return employeeDAO.update(employee);
    }

    // Delete an employee by ID
    public void deleteEmployee(int id) {
        if (getEmployeeById(id) == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employeeDAO.delete(id);
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();
    }

    // Retrieve a specific employee by ID
    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);
    }

    public List<Employee> getEmployeesEligibleForRetirement() {
        List<Employee> allEmployees = getAllEmployees();
        return allEmployees.stream()
                .filter(this::isEligibleForRetirement)
                .toList();
    }
}

