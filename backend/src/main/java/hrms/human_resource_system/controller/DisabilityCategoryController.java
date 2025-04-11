package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.DisabilityCategory;
import main.java.hrms.human_resource_system.service.DisabilityCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disability-categories")
public class DisabilityCategoryController {

    private final DisabilityCategoryService service;

    // Constructor injection
    public DisabilityCategoryController(DisabilityCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<DisabilityCategory> categories = service.getAll();
            return ResponseEntity.ok(categories);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving disability categories", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            DisabilityCategory category = service.getById(id);
            return category != null
                    ? ResponseEntity.ok(category)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomErrorResponse("Disability category not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving disability category", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody DisabilityCategory category) {
        try {
            DisabilityCategory createdCategory = service.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating disability category", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DisabilityCategory category) {
        try {
            // Ensure path ID matches the entity ID if present in body
            if (category.getId() != null && category.getId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }
            category.setId(id);
            
            DisabilityCategory updatedCategory = service.update(category);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating disability category", 500));
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
                    .body(new CustomErrorResponse("Error deleting disability category", 500));
        }
    }
}