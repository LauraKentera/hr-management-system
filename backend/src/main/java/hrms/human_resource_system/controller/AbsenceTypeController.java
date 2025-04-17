package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.AbsenceTypeResponseDTO;
import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.mapper.AbsenceTypeMapper;
import main.java.hrms.human_resource_system.model.AbsenceType;
import main.java.hrms.human_resource_system.service.AbsenceTypeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/absence-types")
public class AbsenceTypeController {

    private final AbsenceTypeService absenceTypeService;

    public AbsenceTypeController(AbsenceTypeService absenceTypeService) {
        this.absenceTypeService = absenceTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAbsenceTypes() {
        try {
            List<AbsenceType> types = absenceTypeService.getAll();
            List<AbsenceTypeResponseDTO> dtoList = types.stream()
                    .map(AbsenceTypeMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving absence types", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            AbsenceType type = absenceTypeService.getById(id);
            if (type != null) {
                return ResponseEntity.ok(AbsenceTypeMapper.toDTO(type));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomErrorResponse("Absence type not found with id: " + id, 404));
            }
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving absence type", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AbsenceType absenceType, @RequestParam int performedBy) {
        try {
            absenceTypeService.insert(absenceType, performedBy); // Pass performedBy
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Absence type created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating absence type", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody AbsenceType updatedType, @RequestParam int performedBy) {
        try {
            updatedType.setAbsenceTypeId(id);
            absenceTypeService.update(id, updatedType, performedBy); // Pass performedBy
            return ResponseEntity.ok("✏️ Absence type updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating absence type", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @RequestParam int performedBy) {
        try {
            absenceTypeService.delete(id, performedBy); // Pass performedBy
            return ResponseEntity.ok("🗑️ Absence type deleted.");
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
                    .body(new CustomErrorResponse("Error deleting absence type", 500));
        }
    }
}
