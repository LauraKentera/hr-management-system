package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.repository.DepartmentDAO;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Validate required fields, logical conditions, and entity existence before insert/update
    private void validateEmployee(Employee employee) {
        if (employee.getPIN() == null || employee.getPIN().isEmpty()) {
            throw new IllegalArgumentException("PIN is required");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
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
        employeeDAO.insert(employee);
    }

    // Update an existing employee by ID
    public void updateEmployee(int id, Employee employee) {
        validateEmployee(employee);
        employeeDAO.update(id, employee);
    }

    // Delete an employee by ID
    public void deleteEmployee(int id) {
        employeeDAO.delete(id);
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();  // Fetch all employees from the database
    }

    // Retrieve a specific employee by ID
    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);  // Fetch a specific employee by ID
    }
}
