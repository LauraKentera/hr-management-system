package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Position;
import main.java.hrms.human_resource_system.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")  // Set the URL path for the endpoints
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // GET all positions
    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    // GET position by ID
    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable int id) {
        Position position = positionService.getPositionById(id);
        if (position != null) {
            return ResponseEntity.ok(position);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST a new position
    @PostMapping
    public ResponseEntity<String> createPosition(@RequestBody Position position) {
        positionService.addPosition(position);
        return ResponseEntity.status(201).body("Position created successfully.");
    }

    // PUT update a position
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePosition(@PathVariable int id, @RequestBody Position position) {
        position.setPositionId(id);  // Ensure the correct ID is set
        positionService.updatePosition(position);
        return ResponseEntity.ok("Position updated successfully.");
    }

    // DELETE a position
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosition(@PathVariable int id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok("Position deleted successfully.");
    }
}
