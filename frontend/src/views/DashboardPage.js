import React, { useEffect, useState } from "react";
import {
    Grid,
    Box,
    Paper,
    Typography,
    Container,
    Divider
} from "@mui/material";
import RetentionChart from "../components/RetentionChart";
import PayrollDistributionChart from "../components/PayrollDistributionChart";
import Header from '../components/Topbar';
import DashboardService from "../services/dashboardService";

const Dashboard = () => {
    const username = localStorage.getItem("username") || "User";
    const [dashboardData, setDashboardData] = useState({
        totalEmployees: 0,
        activeContracts: 0,
        averageSalary: null
    });

    useEffect(() => {
        DashboardService.getOverview()
            .then((response) => {
                console.log("Overview data:", response.data);
                setDashboardData(response.data);
            })
            .catch((error) => {
                console.error("Failed to fetch dashboard overview", error);
            });
    }, []);

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
           <Header /> {/* Add the Header (Topbar) component */}
            <Box mb={4}>
                <Typography variant="h4" fontWeight={700} gutterBottom>
                    Welcome Back, {username}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Track and manage employee stats, payrolls, absence data, and much more all in one place.
                </Typography>
            </Box>

            {/* KPI Cards */}
            <Grid container spacing={3} sx={{ mb: 4 }}>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
                        <Typography variant="subtitle1" fontWeight={600} gutterBottom>
                            Total Employees
                        </Typography>
                        <Typography variant="h4">{dashboardData.totalEmployees}</Typography>
                    </Paper>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
                        <Typography variant="subtitle1" fontWeight={600} gutterBottom>
                            Active Contracts
                        </Typography>
                        <Typography variant="h4">{dashboardData.activeContracts}</Typography>
                    </Paper>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
                        <Typography variant="subtitle1" fontWeight={600} gutterBottom>
                            Average Salary
                        </Typography>
                        <Typography variant="h4">
                            {typeof dashboardData.averageSalary === "number"
                                ? `$${dashboardData.averageSalary.toFixed(2)}`
                                : "Loading..."}
                        </Typography>
                    </Paper>
                </Grid>
            </Grid>

            {/* Charts */}
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2, height: '100%' }}>
                        <Typography variant="h6" fontWeight={600} gutterBottom>
                            Retention Overview
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <RetentionChart />
                    </Paper>
                </Grid>
                <Grid item xs={12} md={6}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2, height: '100%' }}>
                        <Typography variant="h6" fontWeight={600} gutterBottom>
                            Payroll Distribution
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <PayrollDistributionChart />
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Dashboard;
