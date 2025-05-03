package hrms.human_resource_system.controller;

import hrms.human_resource_system.exception.CustomErrorResponse;
import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Department;
import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.service.DepartmentService;
import hrms.human_resource_system.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;


    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    // Constructor injection
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving employees", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return employee != null
                    ? ResponseEntity.ok(employee)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Employee not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving employee", 500));
        }
    }

    @GetMapping("/{id}/retirement-status")
    public ResponseEntity<?> getRetirementStatus(@PathVariable int id) {
        try {
            boolean isEligible = employeeService.isEligibleForRetirement(id);
            Map<String, Object> response = new HashMap<>();
            response.put("employeeId", id);
            response.put("isEligibleForRetirement", isEligible);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error checking retirement status", 500));
        }
    }

    @GetMapping("/retirement-eligible")
    public ResponseEntity<?> getRetirementEligibleEmployees() {
        try {
            List<Employee> eligibleEmployees = employeeService.getEmployeesEligibleForRetirement();
            return ResponseEntity.ok(eligibleEmployees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving retirement-eligible employees", 500));
        }
    }

    // POST new employee
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            // Log incoming employee data to check departmentId and other fields
            logger.debug("Received request to create employee: {}", employee);

            int performedBy = 1; // Replace with the actual user's ID who performs the operation

            // Ensure the department is not null
            if (employee.getDepartment() == null || employee.getDepartment().getDepartmentId() == null) {
                logger.error("Department information is missing for employee creation.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CustomErrorResponse("Department information is required", 400));
            }


            if (employee.getPIN() == null || employee.getPIN().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CustomErrorResponse("PIN is required", 400));
            }

            // Log department ID from request body
            logger.debug("Department ID from employee request: {}", employee.getDepartment().getDepartmentId());

            // Fetch the department by departmentId
            Department department = departmentService.getById(employee.getDepartment().getDepartmentId());
            if (department == null) {
                logger.error("Department with ID {} not found.", employee.getDepartment().getDepartmentId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CustomErrorResponse("Invalid department ID", 400));
            }


            // Log the fetched department details
            logger.debug("Fetched department: {}", department);

            // Set the fetched department to the employee object
            employee.setDepartment(department);

            // Now create the employee
            Employee createdEmployee = employeeService.createEmployee(employee, performedBy);
            logger.debug("Employee created successfully: {}", createdEmployee);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);

        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException while creating employee: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            logger.error("Database error while creating employee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            logger.error("Unexpected error while creating employee: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating employee", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        try {
            // Ensure path ID matches the entity ID if present in body
            if (employee.getId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }
            // Assuming "performedBy" is provided or fetched from authenticated user context
            int performedBy = 1; // Replace with the actual user's ID who performs the operation
            employee.setId(id);
            Employee updatedEmployee = employeeService.updateEmployee(id, employee, performedBy);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating employee", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        try {
            // Assuming "performedBy" is provided or fetched from authenticated user context
            int performedBy = 1; // Replace with the actual user's ID who performs the operation
            employeeService.deleteEmployee(id, performedBy);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(e.getMessage(), 404));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse(e.getMessage(), 409));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error deleting employee", 500));
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable int userId) {
        try {
            Employee employee = employeeService.getByUserId(userId);
            return employee != null
                    ? ResponseEntity.ok(employee)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Employee not found for user ID: " + userId, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving employee by user ID", 500));
        }
    }

}
