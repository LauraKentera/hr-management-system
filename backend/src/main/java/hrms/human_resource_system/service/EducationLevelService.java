package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EducationLevel;
import main.java.hrms.human_resource_system.repository.EducationLevelDAO;

import java.util.List;

public class EducationLevelService {

    private final EducationLevelDAO dao = new EducationLevelDAO();

    public EducationLevel getById(int id) {
        return dao.getById(id);
    }

    public List<EducationLevel> getAll() {
        return dao.getAll();
    }

    public void insert(EducationLevel educationLevel) {
        // Validate before inserting
        validateEducationLevel(educationLevel);
        dao.insert(educationLevel);
    }

    public void update(int id, EducationLevel educationLevel) {
        // Validate before updating
        validateEducationLevel(educationLevel);
        dao.update(id, educationLevel);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    private void validateEducationLevel(EducationLevel educationLevel) {
        // Validate required fields
        if (educationLevel.getName() == null || educationLevel.getName().isEmpty()) {
            throw new IllegalArgumentException("Education Level name is required.");
        }

        // Example: Add any other validation as needed.
        // For example, you might check if the Education Level name is unique.
        // if (dao.existsByName(educationLevel.getName())) {
        //     throw new IllegalArgumentException("Education Level name must be unique.");
        // }
    }
}
