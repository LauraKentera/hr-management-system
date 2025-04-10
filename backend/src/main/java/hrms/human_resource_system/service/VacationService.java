package main.java.hrms.human_resource_system.service;

import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.EmployeeAbsence;
import main.java.hrms.human_resource_system.repository.EmployeeDAO;
import main.java.hrms.human_resource_system.repository.EmployeeAbsenceDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VacationService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final EmployeeAbsenceDAO employeeAbsenceDAO = new EmployeeAbsenceDAO();

    public int calculateVacationDays(int employeeId) {
        Employee employee = employeeDAO.getById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found for ID: " + employeeId);
        }

        // Hardcoded base days by employment type
        int baseDays = switch (employee.getEmploymentType()) {
            case "Full-Time" -> 20;
            case "Part-Time" -> 10;
            default -> 0;
        };

        long yearsOfService = ChronoUnit.YEARS.between(employee.getDateOfHire(), LocalDate.now());
        int bonusDays = (int) (yearsOfService / 5);

        List<EmployeeAbsence> absences = employeeAbsenceDAO.getByEmployeeId(employeeId);
        int unpaidAbsenceDays = absences.stream()
            .filter(a -> !a.getAbsenceType().isPaid()) // only unpaid
            .mapToInt(a -> (int) ChronoUnit.DAYS.between(a.getStartDate(), a.getEndDate().plusDays(1)))
            .sum();

        return Math.max(0, baseDays + bonusDays - unpaidAbsenceDays);
    }
}
