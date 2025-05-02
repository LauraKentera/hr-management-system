package hrms.human_resource_system.controller;

import hrms.human_resource_system.dto.PositionRequestDTO;
import hrms.human_resource_system.dto.PositionResponseDTO;
import hrms.human_resource_system.exception.CustomErrorResponse;
import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.mapper.PositionMapper;
import hrms.human_resource_system.model.Position;
import hrms.human_resource_system.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPositions() {
        try {
            List<Position> positions = positionService.getAllPositions();
            List<PositionResponseDTO> responseDTOs = positions.stream()
                    .map(PositionMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving positions", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPositionById(@PathVariable int id) {
        try {
            Position position = positionService.getPositionById(id);
            if (position != null) {
                PositionResponseDTO responseDTO = PositionMapper.toResponseDTO(position);
                return ResponseEntity.ok(responseDTO);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Position not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving position", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> createPosition(@RequestBody PositionRequestDTO positionRequestDTO) {
        try {
            Position position = PositionMapper.fromRequestDTO(positionRequestDTO);
            int performedBy = getCurrentUserId(); // Simulated user ID

            Position created = positionService.addPosition(position, performedBy);
            PositionResponseDTO responseDTO = PositionMapper.toResponseDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating position", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable int id, @RequestBody PositionRequestDTO positionRequestDTO) {
        try {
            Position position = PositionMapper.fromRequestDTO(positionRequestDTO);
            position.setPositionId(id);
            int performedBy = getCurrentUserId(); // Simulated user ID

            Position updated = positionService.updatePosition(position, performedBy);
            PositionResponseDTO responseDTO = PositionMapper.toResponseDTO(updated);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating position", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable int id) {
        try {
            int performedBy = getCurrentUserId(); // Simulated user ID

            positionService.deletePosition(id, performedBy);
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
                    .body(new CustomErrorResponse("Error deleting position", 500));
        }
    }

    // Dummy method: replace with actual user ID retrieval later
    private int getCurrentUserId() {
        return 1; // simulate an "admin" user
    }
}
