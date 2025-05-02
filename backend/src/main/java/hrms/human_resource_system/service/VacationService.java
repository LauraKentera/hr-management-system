package hrms.human_resource_system.service;

import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.model.EmployeeAbsence;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.EmployeeAbsenceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VacationService {

    private final EmployeeDAO employeeDAO;
    private final EmployeeAbsenceDAO employeeAbsenceDAO;

    @Autowired
    public VacationService(EmployeeDAO employeeDAO, EmployeeAbsenceDAO employeeAbsenceDAO) {
        this.employeeDAO = employeeDAO;
        this.employeeAbsenceDAO = employeeAbsenceDAO;
    }

    public int calculateVacationDays(int employeeId) {
        Employee employee = employeeDAO.getById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found for ID: " + employeeId);
        }

        int baseDays = switch (employee.getEmploymentType()) {
            case "Full-Time" -> 20;
            case "Part-Time" -> 10;
            default -> 0;
        };

        long yearsOfService = ChronoUnit.YEARS.between(employee.getDateOfHire(), LocalDate.now());
        int bonusDays = (int) (yearsOfService / 5); // 1 extra day every 5 years

        List<EmployeeAbsence> absences = employeeAbsenceDAO.getByEmployeeId(employeeId);
        int unpaidAbsenceDays = absences.stream()
                .filter(a -> !a.getAbsenceType().isPaid())
                .mapToInt(a -> (int) ChronoUnit.DAYS.between(a.getStartDate(), a.getEndDate().plusDays(1)))
                .sum();

        return Math.max(0, baseDays + bonusDays - unpaidAbsenceDays);
    }
}
