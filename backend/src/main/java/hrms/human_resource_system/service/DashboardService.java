package hrms.human_resource_system.service;

import hrms.human_resource_system.dto.DashboardDTO;
import hrms.human_resource_system.model.Benefit;
import hrms.human_resource_system.model.Payroll;
import hrms.human_resource_system.repository.EmployeeDAO;
import hrms.human_resource_system.repository.ContractDAO;
import hrms.human_resource_system.repository.BenefitDAO;
import hrms.human_resource_system.repository.PayrollDAO;
import hrms.human_resource_system.repository.AbsenceTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private BenefitDAO benefitDAO;

    @Autowired
    private PayrollDAO payrollDAO;

    @Autowired
    private AbsenceTypeDAO absenceTypeDAO;

    // Method to get overall dashboard data (overview)
    public DashboardDTO getOverviewData() {
        DashboardDTO dashboardDTO = new DashboardDTO();

        dashboardDTO.setTotalEmployees(getTotalEmployees());
        dashboardDTO.setActiveContracts(getActiveContracts());
        dashboardDTO.setPendingBenefits(getPendingBenefits());
        dashboardDTO.setEmployeeAbsencesToday(getEmployeeAbsencesToday());
        dashboardDTO.setAverageSalary(getAverageSalary());
        dashboardDTO.setEmployeeRetentionRate(getEmployeeRetentionRate());
        dashboardDTO.setBenefitsUtilization(getBenefitsUtilization());
        dashboardDTO.setContractExpirations(getContractExpirations());
        dashboardDTO.setNextPayCycle(getNextPayCycle());

        // ➕ Include per-employee net pay for pie chart
        dashboardDTO.setNetPayByEmployee(getNetPayByEmployee());

        return dashboardDTO;
    }


    // Method to get total number of employees
    public long getTotalEmployees() {
        return employeeDAO.getAll().size();
    }

    // Method to get active contracts count
    public long getActiveContracts() {
        return contractDAO.getAll().stream()
                .filter(contract -> contract.getEndDate() == null || contract.getEndDate().isAfter(LocalDate.now()))
                .count();
    }

    // Method to get the number of pending benefits
    public long getPendingBenefits() {
        return benefitDAO.getAll().stream()
                .filter(benefit -> benefit.isActive() && !benefit.isTaxable())
                .count();
    }

    // Method to get employee absences for today
    public long getEmployeeAbsencesToday() {
        // Assuming absenceTypeDAO contains the absence data and there is a method to check absences today
        return absenceTypeDAO.getAll().stream()
                .filter(absence -> absence.isActive() && absence.isPaid())
                .count();  // You can adjust this depending on your business rules
    }

    // Method to calculate the average salary
    public double getAverageSalary() {
        var payrolls = payrollDAO.getAll();
        BigDecimal totalSalary = BigDecimal.ZERO;
        int count = 0;

        for (var payroll : payrolls) {
            if (payroll.getBaseSalary() != null) {
                totalSalary = totalSalary.add(payroll.getBaseSalary());
                count++;
            }
        }

        return count > 0
                ? totalSalary.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP).doubleValue()
                : 0;
    }

    // Method to calculate employee retention rate
    public double getEmployeeRetentionRate() {
        // Assuming employeeDAO contains the data and we calculate retention based on some logic
        long totalEmployees = employeeDAO.getAll().size();
        long retainedEmployees = employeeDAO.getAll().stream()
                .filter(employee -> employee.getDateOfDismissal() == null || employee.getDateOfDismissal().isAfter(LocalDate.now().minusYears(1)))
                .count();

        return totalEmployees > 0 ? (double) retainedEmployees / totalEmployees * 100 : 0;
    }

    // Method to calculate benefits utilization rate
    public double getBenefitsUtilization() {
        long totalBenefits = benefitDAO.getAll().size();
        long usedBenefits = benefitDAO.getAll().stream()
                .filter(Benefit::isActive) // assuming 'isActive' signifies utilization
                .count();

        return totalBenefits > 0 ? (double) usedBenefits / totalBenefits * 100 : 0;
    }

    // Method to get contract expirations within the next 30 days
    public long getContractExpirations() {
        return contractDAO.getAll().stream()
                .filter(contract -> contract.getEndDate() != null && contract.getEndDate().isBefore(LocalDate.now().plusDays(30)))
                .count();
    }

    // Method to get the next payroll cycle date
    public String getNextPayCycle() {
        // Assuming that payroll data contains the next payroll cycle (you can adjust this based on your business logic)
        return payrollDAO.getAll().stream()
                .filter(payroll -> payroll.getPaymentDate() != null)
                .map(payroll -> payroll.getPaymentDate().toString())
                .findFirst()
                .orElse("N/A");
    }

    public Map<String, Double> getNetPayByEmployee() {
        // Assuming you already have a list of Payroll objects
        Map<String, Double> salaryRanges = new HashMap<>();

        // Loop through each payroll entry and categorize into salary ranges
        for (Payroll payroll : payrollDAO.getAll()) {
            double netPay = payroll.getNetPay().doubleValue();

            if (netPay < 1000) {
                salaryRanges.put("Below 1k", salaryRanges.getOrDefault("Below 1k", 0.0) + netPay);
            } else if (netPay >= 1000 && netPay < 2000) {
                salaryRanges.put("1k-2k", salaryRanges.getOrDefault("1k-2k", 0.0) + netPay);
            } else if (netPay >= 2000 && netPay < 3000) {
                salaryRanges.put("2k-3k", salaryRanges.getOrDefault("2k-3k", 0.0) + netPay);
            } else if (netPay >= 3000 && netPay < 5000) {
                salaryRanges.put("3k-5k", salaryRanges.getOrDefault("3k-5k", 0.0) + netPay);
            } else {
                salaryRanges.put("5k+", salaryRanges.getOrDefault("5k+", 0.0) + netPay);
            }
        }

        return salaryRanges;
    }
}
