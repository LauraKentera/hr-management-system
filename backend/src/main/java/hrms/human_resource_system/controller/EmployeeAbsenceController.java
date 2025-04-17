package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.service.EmployeeAbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-absences")
public class EmployeeAbsenceController {

    private final EmployeeAbsenceService service;

    public EmployeeAbsenceController(EmployeeAbsenceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<EmployeeAbsence> absences = service.getAll();
            return ResponseEntity.ok(absences);
        } catch (DLException e) {
            return error("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return error("Error retrieving employee absences", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            EmployeeAbsence absence = service.getById(id);
            return absence != null
                    ? ResponseEntity.ok(absence)
                    : error("Employee absence not found with id: " + id, HttpStatus.NOT_FOUND);
        } catch (DLException e) {
            return error("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return error("Error retrieving employee absence", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeAbsence absence) {
        try {
            EmployeeAbsence created = service.create(absence);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DLException e) {
            return error("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return error("Error creating employee absence", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody EmployeeAbsence absence) {
        try {
            if (absence.getAbsenceId() != null && absence.getAbsenceId() != id) {
                return error("ID in path does not match ID in request body", HttpStatus.BAD_REQUEST);
            }
            absence.setAbsenceId(id);
            EmployeeAbsence updated = service.update(absence);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DLException e) {
            return error("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return error("Error updating employee absence", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (DLException e) {
            return error("Database error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return error("Error deleting employee absence", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<CustomErrorResponse> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new CustomErrorResponse(message, status.value()));
    }
}
