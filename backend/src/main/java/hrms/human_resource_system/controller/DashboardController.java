package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.DashboardDTO;
import main.java.hrms.human_resource_system.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/overview")  // This is the endpoint you are trying to access from the frontend
    public DashboardDTO getDashboardOverview() {
        return dashboardService.getOverviewData();  // Ensure this method returns the data correctly
    }
}
