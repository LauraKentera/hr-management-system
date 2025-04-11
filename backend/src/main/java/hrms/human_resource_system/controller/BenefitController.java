package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Benefit;
import main.java.hrms.human_resource_system.service.BenefitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benefits")
public class BenefitController {

    private final BenefitService service;

    // Constructor injection
    public BenefitController(BenefitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Benefit> benefits = service.getAll();
            return ResponseEntity.ok(benefits);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving benefits", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            Benefit benefit = service.getById(id);
            return benefit != null
                    ? ResponseEntity.ok(benefit)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomErrorResponse("Benefit not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving benefit", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Benefit benefit) {
        try {
            Benefit createdBenefit = service.create(benefit); // Changed to create() for consistency
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBenefit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating benefit", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Benefit benefit) {
        try {
            // Ensure path ID matches the entity ID if present in body
            if (benefit.getId() != null && benefit.getId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }
            
            Benefit updatedBenefit = service.update(id, benefit);
            return ResponseEntity.ok(updatedBenefit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating benefit", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            service.delete(id);
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
                    .body(new CustomErrorResponse("Error deleting benefit", 500));
        }
    }
}