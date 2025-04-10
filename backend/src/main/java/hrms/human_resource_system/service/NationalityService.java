package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Nationality;
import main.java.hrms.human_resource_system.repository.NationalityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationalityService {

    private final NationalityDAO nationalityDAO;

    @Autowired
    public NationalityService(NationalityDAO nationalityDAO) {
        this.nationalityDAO = nationalityDAO;
    }

    public List<Nationality> getAllNationalities() {
        return nationalityDAO.getAll();
    }

    public Nationality getNationalityById(Integer nationalityId) {
        if (nationalityId == null) {
            throw new IllegalArgumentException("Nationality ID cannot be null");
        }
        return nationalityDAO.getById(nationalityId);
    }

    public void addNationality(Nationality nationality) {
        validateNationality(nationality); // validate before inserting
        nationalityDAO.insert(nationality);
    }

    public void updateNationality(Nationality nationality) {
        validateNationality(nationality); // validate before updating
        nationalityDAO.update(nationality);
    }

    public void deleteNationality(Integer nationalityId) {
        if (nationalityId == null) {
            throw new IllegalArgumentException("Nationality ID cannot be null");
        }
        nationalityDAO.delete(nationalityId);
    }

    private void validateNationality(Nationality nationality) {
        if (nationality.getName() == null || nationality.getName().isEmpty()) {
            throw new IllegalArgumentException("Nationality name cannot be null or empty");
        }

        if (nationality.getUserId() == null || nationality.getUserId() <= 0) {
            throw new IllegalArgumentException("User ID must be valid");
        }

        if (nationality.getIsActive() == null) {
            throw new IllegalArgumentException("IsActive flag must be specified");
        }
    }
}
