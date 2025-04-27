package hrms.human_resource_system.controller;

import hrms.human_resource_system.exception.CustomErrorResponse;
import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EducationLevel;
import hrms.human_resource_system.service.EducationLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education-levels")
public class EducationLevelController {

    private final EducationLevelService service;

    // Constructor injection
    public EducationLevelController(EducationLevelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<EducationLevel> educationLevels = service.getAll();
            return ResponseEntity.ok(educationLevels);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving education levels", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            EducationLevel educationLevel = service.getById(id);
            return educationLevel != null
                    ? ResponseEntity.ok(educationLevel)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Education level not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving education level", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody EducationLevel educationLevel) {
        try {
            service.insert(educationLevel);
            return ResponseEntity.status(HttpStatus.CREATED).body(educationLevel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating education level", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody EducationLevel educationLevel) {
        try {
            if (educationLevel.getEducationLevelId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }
            service.update(id, educationLevel);
            return ResponseEntity.ok(educationLevel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating education level", 500));
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
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error deleting education level", 500));
        }
    }
}
