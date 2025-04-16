package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.EmployeeEvaluationRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeEvaluationResponseDTO;
import main.java.hrms.human_resource_system.exception.CustomErrorResponse;
import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.mapper.EmployeeEvaluationMapper;
import main.java.hrms.human_resource_system.model.EmployeeEvaluation;
import main.java.hrms.human_resource_system.service.EmployeeEvaluationService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee-evaluations")
public class EmployeeEvaluationController {

    private final EmployeeEvaluationService service;

    public EmployeeEvaluationController(EmployeeEvaluationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvaluations() {
        try {
            List<EmployeeEvaluation> evaluations = service.getAll();
            List<EmployeeEvaluationResponseDTO> dtoList = evaluations.stream()
                    .map(EmployeeEvaluationMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving evaluations", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvaluationById(@PathVariable int id) {
        try {
            EmployeeEvaluation eval = service.getById(id);
            if (eval != null) {
                return ResponseEntity.ok(EmployeeEvaluationMapper.toDTO(eval));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomErrorResponse("Evaluation not found with id: " + id, 404));
            }
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving evaluation", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvaluation(@RequestBody EmployeeEvaluationRequestDTO dto, @RequestParam int performedBy) {
        try {
            EmployeeEvaluation eval = EmployeeEvaluationMapper.toEntity(dto);
            service.insert(eval, performedBy);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Evaluation created.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating evaluation", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvaluation(@PathVariable int id, @RequestBody EmployeeEvaluationRequestDTO dto, @RequestParam int performedBy) {
        try {
            EmployeeEvaluation eval = EmployeeEvaluationMapper.toEntity(dto);
            eval.setId(id);
            service.update(id, eval, performedBy);
            return ResponseEntity.ok("✏ Evaluation updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating evaluation", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvaluation(@PathVariable int id, @RequestParam int performedBy) {
        try {
            service.delete(id, performedBy);
            return ResponseEntity.ok("🗑 Evaluation deleted.");
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
                    .body(new CustomErrorResponse("Error deleting evaluation", 500));
        }
    }
}
