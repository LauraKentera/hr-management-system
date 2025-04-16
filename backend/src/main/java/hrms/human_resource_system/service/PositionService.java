package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Position;
import main.java.hrms.human_resource_system.repository.PositionDAO;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final PositionDAO positionDAO;

    @Autowired
    public PositionService(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    // Wrapper method for consistent exception handling
    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;  // Let validation errors bubble up
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    // Get all positions
    public List<Position> getAllPositions() {
        return wrap(positionDAO::getAll);
    }

    // Get position by ID
    public Position getPositionById(int id) {
        return wrap(() -> positionDAO.getById(id));
    }

    public Position addPosition(Position position) {
        wrap(() -> {
            validatePosition(position); // Validate before inserting
            positionDAO.insert(position);
            return position;
        });
    }

    // Update an existing position
    public void updatePosition(Position position) {
        wrap(() -> {
            validatePosition(position); // Validate before updating
            positionDAO.update(position);
            return null;  // Return type is Void
        });
    }

    // Delete position by ID
    public void deletePosition(int id) {
        wrap(() -> {
            positionDAO.delete(id);
            return null;  // Return type is Void
        });
    }

    // Validation method for Position
    private void validatePosition(Position position) {
        if (position.getName() == null || position.getName().isEmpty()) {
            throw new IllegalArgumentException("Position name is required.");
        }
        if (position.getShortName() == null || position.getShortName().isEmpty()) {
            throw new IllegalArgumentException("Position short name is required.");
        }
        if (position.getEducationLevelId() <= 0) {
            throw new IllegalArgumentException("Invalid education level ID.");
        }
        // Add other validations if needed (e.g., checking for circular references in parent-child positions, etc.)
    }
}
