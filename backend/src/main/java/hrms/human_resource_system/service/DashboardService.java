package hrms.human_resource_system.service;

import hrms.human_resource_system.dto.DashboardDTO;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    // Inject your repositories here

    public DashboardDTO getOverviewData() {
        // Fetch data for attendance, late arrivals, absents, leave applies, etc. from the database
        int attendance = 57; // Sample data
        int lateArrivals = 23;
        int absents = 3;
        int leaveApplied = 6;

        // Populate the DTO with the data
        DashboardDTO dashboardDTO = new DashboardDTO(attendance, lateArrivals, absents, leaveApplied);
        return dashboardDTO;
    }
}
