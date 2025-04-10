package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.EmployeeBenefit;
import main.java.hrms.human_resource_system.repository.EmployeeBenefitDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeBenefitService {

    private final EmployeeBenefitDAO employeeBenefitDAO;

    // Constructor Injection for EmployeeBenefitDAO
    public EmployeeBenefitService(EmployeeBenefitDAO employeeBenefitDAO) {
        this.employeeBenefitDAO = employeeBenefitDAO;
    }

    // Retrieve all EmployeeBenefits
    public List<EmployeeBenefit> getAllEmployeeBenefits() {
        return employeeBenefitDAO.getAll();
    }

    // Retrieve an EmployeeBenefit by ID
    public EmployeeBenefit getEmployeeBenefitById(int id) {
        return employeeBenefitDAO.getById(id);
    }

    // Add a new EmployeeBenefit after validation
    public void addEmployeeBenefit(EmployeeBenefit employeeBenefit) {
        validateEmployeeBenefit(employeeBenefit);  // Validation before inserting
        employeeBenefitDAO.insert(employeeBenefit);
    }

    // Update an existing EmployeeBenefit after validation
    public void updateEmployeeBenefit(int id, EmployeeBenefit employeeBenefit) {
        validateEmployeeBenefit(employeeBenefit);  // Validation before updating
        employeeBenefitDAO.update(id, employeeBenefit);
    }

    // Delete an EmployeeBenefit by ID
    public void deleteEmployeeBenefit(int id) {
        employeeBenefitDAO.delete(id);
    }

    // Validation for EmployeeBenefit
    private void validateEmployeeBenefit(EmployeeBenefit employeeBenefit) {
        if (employeeBenefit.getEmployee() == null || employeeBenefit.getEmployee().getId() <= 0) {
            throw new IllegalArgumentException("Valid Employee is required.");
        }
        if (employeeBenefit.getBenefit() == null || employeeBenefit.getBenefit().getBenefitId() <= 0) {
            throw new IllegalArgumentException("Valid Benefit is required.");
        }
        if (employeeBenefit.getFromDate() == null) {
            throw new IllegalArgumentException("From Date is required.");
        }
        if (employeeBenefit.getToDate() != null && employeeBenefit.getFromDate().isAfter(employeeBenefit.getToDate())) {
            throw new IllegalArgumentException("From Date cannot be after To Date.");
        }
        // Additional validation checks can be added as needed
    }
}
