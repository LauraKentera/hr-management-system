import React from "react";
import { Grid, Box, Paper, Typography, Container, Divider } from "@mui/material";
import RetentionChart from "../components/RetentionChart";
import PayrollDistributionChart from "../components/PayrollDistributionChart";

const Dashboard = () => {
    const username = localStorage.getItem("username") || "User";

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            {/* Header */}
            <Box mb={4}>
                <Typography variant="h4" fontWeight={700} gutterBottom>
                    Welcome Back, {username}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Track and manage employee stats, payrolls, absence data, and much more all in one place.
                </Typography>
            </Box>

            {/* KPI Cards Row - Top Section */}
            <Grid container spacing={3} sx={{ mb: 4 }}>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
                        <Typography variant="subtitle1" fontWeight={600} gutterBottom>
                            Total Employees
                        </Typography>
                        <Typography variant="h4">3</Typography>
                    </Paper>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
                        <Typography variant="subtitle1" fontWeight={600} gutterBottom>
                            Active Contracts
                        </Typography>
                        <Typography variant="h4">0</Typography>
                    </Paper>
                </Grid>
                <Grid item xs={12} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 2, boxShadow: 2 }}>
                        <Typography variant="subtitle1" fontWeight={600} gutterBottom>
                            Average Salary
                        </Typography>
                        <Typography variant="h4">$10.00</Typography>
                    </Paper>
                </Grid>
            </Grid>

            {/* Charts Section - Bottom Section */}
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