package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Position;
import main.java.hrms.human_resource_system.repository.PositionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    private final PositionDAO positionDAO;

    @Autowired
    public PositionService(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    public List<Position> getAllPositions() {
        return positionDAO.getAll();
    }

    public Position getPositionById(int id) {
        return positionDAO.getById(id);
    }

    public void addPosition(Position position) {
        // Validate position before inserting
        validatePosition(position);
        positionDAO.insert(position);
    }

    public void updatePosition(Position position) {
        // Validate position before updating
        validatePosition(position);
        positionDAO.update(position);
    }

    public void deletePosition(int id) {
        positionDAO.delete(id);
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
