import React, { useEffect, useState } from 'react';
import { Container, Grid, Typography, Box } from '@mui/material';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header component
import AttendanceGraph from '../components/AttendanceGraph';  // Import the Attendance Graph component
import KPICard from '../components/KPICard'; // Import KPICard component
import '../styles/Dashboard.css';
import EmployeeStatusChart from "../components/EmployeeStatusChart";
import AbsenceTypeChart from "../components/AbsenceTypeChart";
import SalaryByDepartmentChart from "../components/SalaryByDepartmentChart";
import PayrollExpensesChart from "../components/PayrollExpensesChart";
import EmployeeGrowthChart from "../components/EmployeeGrowthChart";
import UpcomingContractsTable from "../components/UpcomingContractsTable"; // Import custom CSS for styling

const DashboardPage = () => {
    const [dashboardData, setDashboardData] = useState({
        attendance: 0,
        lateArrivals: 0,
        absents: 0,
        leaveApplied: 0,
    });

    // Simulating some KPI data
    const [kpiData, setKpiData] = useState({
        employeeTurnoverRate: 5, // % turnover
        absenteeismRate: 3, // % absenteeism
        employeeEngagement: 80, // % of engagement
        leaveApplications: 20, // total leave applications
    });

    useEffect(() => {
        // Simulate fetching dashboard data
        setDashboardData({
            attendance: 95,
            lateArrivals: 5,
            absents: 3,
            leaveApplied: 2,
        });

        // Simulate fetching KPI data
        setKpiData({
            employeeTurnoverRate: 5,
            absenteeismRate: 3,
            employeeEngagement: 80,
            leaveApplications: 20,
        });
    }, []);

    return (
        <div className="dashboard-page">
            {/* Sidebar and Header */}
            <Sidebar />
            <Header />  {/* Add Topbar here */}

            {/* Main Dashboard Content */}
            <Container className="dashboard-container">
                {/* Overview Section */}
                <Box className="overview-box">
                    <Typography variant="h4" gutterBottom>
                        Good Morning, User
                    </Typography>
                    <Typography variant="h6" paragraph>
                        Here's what's happening with the team today
                    </Typography>

                    {/* Employee Metrics (Attendance, Late Arrivals, etc.) */}
                    <Grid container spacing={3}>
                        <Grid item xs={12} sm={6} md={3}>
                            <KPICard title="Employee Turnover" value={`${kpiData.employeeTurnoverRate}%`} color="red" />
                        </Grid>
                        <Grid item xs={12} sm={6} md={3}>
                            <KPICard title="Absenteeism Rate" value={`${kpiData.absenteeismRate}%`} color="orange" />
                        </Grid>
                        <Grid item xs={12} sm={6} md={3}>
                            <KPICard title="Employee Engagement" value={`${kpiData.employeeEngagement}%`} color="green" />
                        </Grid>
                        <Grid item xs={12} sm={6} md={3}>
                            <KPICard title="Leave Applications" value={`${kpiData.leaveApplications}`} color="blue" />
                        </Grid>
                    </Grid>
                </Box>

                {/* Attendance Graph Section */}
                <Box className="attendance-graph-box" mt={4}>
                    <Typography variant="h5" gutterBottom>
                        Attendance Trends (This Week)
                    </Typography>
                    <AttendanceGraph />
                </Box>

                {/* Charts Section - Distributed in 2 Columns */}
                <Box className="charts-section" mt={4}>
                    <Typography variant="h5" gutterBottom>
                        Employee Status Breakdown
                    </Typography>
                    <Grid container spacing={4}>
                        <Grid item xs={12} sm={6} md={4}>
                            <EmployeeStatusChart />
                        </Grid>
                        <Grid item xs={12} sm={6} md={4}>
                            <AbsenceTypeChart />
                        </Grid>
                    </Grid>

                    <Typography variant="h5" gutterBottom mt={4}>
                        Department and Payroll Insights
                    </Typography>
                    <Grid container spacing={4}>
                        <Grid item xs={12} sm={6} md={4}>
                            <SalaryByDepartmentChart />
                        </Grid>
                        <Grid item xs={12} sm={6} md={4}>
                            <PayrollExpensesChart />
                        </Grid>
                    </Grid>

                    <Typography variant="h5" gutterBottom mt={4}>
                        Employee Growth and Upcoming Contracts
                    </Typography>
                    <Grid container spacing={4}>
                        <Grid item xs={12} sm={6} md={4}>
                            <EmployeeGrowthChart />
                        </Grid>
                        <Grid item xs={12} sm={6} md={4}>
                            <UpcomingContractsTable />
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </div>
    );
};

export default DashboardPage;
