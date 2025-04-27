package hrms.human_resource_system.service;

import hrms.human_resource_system.model.Position;
import hrms.human_resource_system.repository.PositionDAO;
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

    public Position addPosition(Position position, int performedBy) {
        wrap(() -> {
            validatePosition(position);
            positionDAO.insert(position, performedBy);
            return position;
        });
        return position;
    }

    public Position updatePosition(Position position, int performedBy) {
        wrap(() -> {
            validatePosition(position);
            positionDAO.update(position, performedBy);
            return position;
        });
        return position;
    }

    public void deletePosition(int id, int performedBy) {
        wrap(() -> {
            positionDAO.delete(id, performedBy);
            return null;
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
