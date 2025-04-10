package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.AbsenceType;
import main.java.hrms.human_resource_system.repository.AbsenceTypeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceTypeService {

    private final AbsenceTypeDAO dao = new AbsenceTypeDAO();

    public AbsenceType getById(int id) {
        return dao.getById(id);
    }

    public List<AbsenceType> getAll() {
        return dao.getAll();
    }

    public void insert(AbsenceType absenceType) {
        // Step 1: Validate required fields
        if (absenceType.getName() == null || absenceType.getName().isEmpty()) {
            throw new IllegalArgumentException("Absence Type Name is required.");
        }

        if (absenceType.getCode() == null || absenceType.getCode().isEmpty()) {
            throw new IllegalArgumentException("Absence Type Code is required.");
        }

        // Step 2: Check if an Absence Type with the same code or name already exists
        if (dao.getAll().stream().anyMatch(existingAbsenceType -> existingAbsenceType.getCode().equals(absenceType.getCode()))) {
            throw new IllegalArgumentException("Absence Type with this code already exists.");
        }

        // Step 3: Call DAO to insert the new absenceType
        dao.insert(absenceType);
    }

    public void update(AbsenceType absenceType) {
        // Step 1: Validate required fields
        if (absenceType.getName() == null || absenceType.getName().isEmpty()) {
            throw new IllegalArgumentException("Absence Type Name is required.");
        }

        if (absenceType.getCode() == null || absenceType.getCode().isEmpty()) {
            throw new IllegalArgumentException("Absence Type Code is required.");
        }

        // Step 2: Call DAO to update the absenceType
        dao.update(absenceType);
    }

    public void delete(int id) {
        // Step 1: Check if the AbsenceType exists before trying to delete
        AbsenceType absenceType = dao.getById(id);
        if (absenceType == null) {
            throw new IllegalArgumentException("Absence Type with ID " + id + " does not exist.");
        }

        // Step 2: Call DAO to delete the absenceType
        dao.delete(id);
    }
}
