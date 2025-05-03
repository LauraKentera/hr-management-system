package hrms.human_resource_system.controller;

import hrms.human_resource_system.dto.DashboardDTO;
import hrms.human_resource_system.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Endpoint to get overall dashboard overview
    @GetMapping("/overview")
    public DashboardDTO getDashboardOverview() {
        return dashboardService.getOverviewData();
    }

    // KPI-specific endpoints (used individually if needed)

    @GetMapping("/total-employees")
    public long getTotalEmployees() {
        return dashboardService.getTotalEmployees();
    }

    @GetMapping("/active-contracts")
    public long getActiveContracts() {
        return dashboardService.getActiveContracts();
    }

    @GetMapping("/pending-benefits")
    public long getPendingBenefits() {
        return dashboardService.getPendingBenefits();
    }

    @GetMapping("/absences-today")
    public long getEmployeeAbsencesToday() {
        return dashboardService.getEmployeeAbsencesToday();
    }

    @GetMapping("/average-salary")
    public double getAverageSalary() {
        return dashboardService.getAverageSalary();
    }

    @GetMapping("/retention-rate")
    public double getEmployeeRetentionRate() {
        return dashboardService.getEmployeeRetentionRate();
    }

    @GetMapping("/benefits-utilization")
    public double getBenefitsUtilization() {
        return dashboardService.getBenefitsUtilization();
    }

    @GetMapping("/contract-expirations")
    public long getContractExpirations() {
        return dashboardService.getContractExpirations();
    }

    @GetMapping("/next-pay-cycle")
    public String getNextPayCycle() {
        return dashboardService.getNextPayCycle();
    }

    @GetMapping("/net-pay-by-employee")
    public Map<String, Double> getNetPayByEmployee() {
        return dashboardService.getNetPayByEmployee(); // Assuming this method fetches the data
    }

}
