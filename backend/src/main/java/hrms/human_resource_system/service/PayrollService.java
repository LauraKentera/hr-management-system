package hrms.human_resource_system.service;

import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.model.EmployeeAbsence;
import hrms.human_resource_system.model.Payroll;
import hrms.human_resource_system.repository.ContractDAO;
import hrms.human_resource_system.repository.EmployeeAbsenceDAO;
import hrms.human_resource_system.repository.PayrollDAO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PayrollService {

    private final EmployeeAbsenceDAO employeeAbsenceDAO = new EmployeeAbsenceDAO();
    private final ContractDAO contractDAO = new ContractDAO();
    private final PayrollDAO payrollDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
    }

    private <T> T wrap(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public Payroll getById(int id) {
        return wrap(() -> payrollDAO.getById(id));
    }

    public List<Payroll> getAll() {
        return wrap(payrollDAO::getAll);
    }

    public Payroll addPayroll(Payroll payroll) {
        return wrap(() -> {
            validatePayroll(payroll);
            payrollDAO.insert(payroll);
            return payroll;
        });
    }

    public Payroll updatePayroll(Payroll payroll) {
        return wrap(() -> {
            validatePayroll(payroll);
            payrollDAO.update(payroll);
            return payroll;
        });
    }

    public void deletePayroll(int payrollId) {
        wrap(() -> {
            payrollDAO.delete(payrollId);
            return null;
        });
    }

    private void validatePayroll(Payroll payroll) {
        if (payroll.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("⛔ Invalid employee ID.");
        }
        if (payroll.getBaseSalary() == null || payroll.getBaseSalary().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("⛔ Salary must be greater than 0.");
        }
        if (payroll.getPeriodStart() == null || payroll.getPeriodEnd() == null) {
            throw new IllegalArgumentException("⛔ Payroll period start and end dates cannot be null.");
        }
        if (!isPayrollPeriodValid(payroll)) {
            throw new IllegalArgumentException("⛔ Payroll start date must be before end date.");
        }
    }

    public boolean isPayrollPeriodValid(Payroll payroll) {
        return payroll.getPeriodStart().isBefore(payroll.getPeriodEnd());
    }

    public BigDecimal calculateNetPay(Employee employee, LocalDate from, LocalDate to) {
        BigDecimal baseSalary = getBaseSalary(employee, from, to);
        BigDecimal bonuses = getBonuses(employee, from, to);
        BigDecimal deductions = getDeductions(employee, from, to);
        BigDecimal benefits = getTaxableBenefits(employee, from, to);

        return baseSalary.add(bonuses).subtract(deductions).add(benefits);
    }

    private BigDecimal getBaseSalary(Employee employee, LocalDate from, LocalDate to) {
        return contractDAO.getBaseSalary(employee.getId(), from, to);
    }

    private BigDecimal getBonuses(Employee employee, LocalDate from, LocalDate to) {
        return BigDecimal.ZERO;
    }

    private BigDecimal getDeductions(Employee employee, LocalDate from, LocalDate to) {
        List<EmployeeAbsence> absences = employeeAbsenceDAO.getByEmployeeId(employee.getId());
        BigDecimal totalDeductions = BigDecimal.ZERO;

        for (EmployeeAbsence absence : absences) {
            if (!absence.getAbsenceType().isPaid()) {
                totalDeductions = totalDeductions.add(calculateAbsenceDeductions(absence));
            }
        }

        return totalDeductions;
    }

    private BigDecimal calculateAbsenceDeductions(EmployeeAbsence absence) {
        long absenceDays = absence.getStartDate().until(absence.getEndDate(), java.time.temporal.ChronoUnit.DAYS);
        BigDecimal dailySalary = getBaseSalary(absence.getEmployee(), absence.getStartDate(), absence.getEndDate())
                .divide(BigDecimal.valueOf(30), 2, java.math.RoundingMode.HALF_UP);
        return dailySalary.multiply(BigDecimal.valueOf(absenceDays));
    }

    private BigDecimal getTaxableBenefits(Employee employee, LocalDate from, LocalDate to) {
        return BigDecimal.ZERO;
    }
}
