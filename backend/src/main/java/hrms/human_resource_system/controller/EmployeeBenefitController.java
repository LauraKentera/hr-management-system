package hrms.human_resource_system.controller;

import hrms.human_resource_system.exception.CustomErrorResponse;
import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmployeeBenefit;
import hrms.human_resource_system.service.EmployeeBenefitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-benefits")
public class EmployeeBenefitController {

    private final EmployeeBenefitService employeeBenefitService;

    public EmployeeBenefitController(EmployeeBenefitService employeeBenefitService) {
        this.employeeBenefitService = employeeBenefitService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployeeBenefits() {
        try {
            List<EmployeeBenefit> employeeBenefits = employeeBenefitService.getAllEmployeeBenefits();
            return ResponseEntity.ok(employeeBenefits);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving employee benefits", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeBenefitById(@PathVariable int id) {
        try {
            EmployeeBenefit employeeBenefit = employeeBenefitService.getEmployeeBenefitById(id);
            return employeeBenefit != null
                    ? ResponseEntity.ok(employeeBenefit)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomErrorResponse("Employee benefit not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving employee benefit", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmployeeBenefit(@RequestBody EmployeeBenefit employeeBenefit) {
        try {
            EmployeeBenefit createdBenefit = employeeBenefitService.createEmployeeBenefit(employeeBenefit);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBenefit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating employee benefit", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeBenefit(@PathVariable int id, @RequestBody EmployeeBenefit employeeBenefit) {
        try {
            // Ensure path ID matches the entity ID if present in body
            if (employeeBenefit.getId() != null && employeeBenefit.getId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }
            employeeBenefit.setId(id);
            
            EmployeeBenefit updatedBenefit = employeeBenefitService.updateEmployeeBenefit(employeeBenefit);
            return ResponseEntity.ok(updatedBenefit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating employee benefit", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeBenefit(@PathVariable int id) {
        try {
            employeeBenefitService.deleteEmployeeBenefit(id);
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
                    .body(new CustomErrorResponse("Error deleting employee benefit", 500));
        }
    }
}