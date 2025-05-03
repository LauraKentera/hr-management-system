import React from "react";
import { Grid, Box, Paper, Typography, Container, Divider } from "@mui/material";
import DashboardKpi from "../components/DashboardKpi";
import RetentionChart from "../components/RetentionChart";
import PayrollDistributionChart from "../components/PayrollDistributionChart";

const Dashboard = () => {
    const username = localStorage.getItem("username") || "User";

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            {/* Header */}
            <Box mb={4}>
                <Typography variant="h4" fontWeight={700} color="primary.main">
                    Hello, {username}
                </Typography>
                <Typography variant="body1" color="text.secondary" mt={1}>
                    Welcome to your dashboard. Track employee stats, contracts, payrolls, and more.
                </Typography>
            </Box>

            <Grid container spacing={3}>
                {/* KPI Cards */}
                <Grid item xs={12}>
                    <Paper
                        elevation={4}
                        sx={{
                            p: 3,
                            borderRadius: 3,
                            backgroundColor: "#fdfdfd",
                            boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.06)",
                        }}
                    >
                        <DashboardKpi />
                    </Paper>
                </Grid>

                {/* Retention Chart */}
                <Grid item xs={12} md={6}>
                    <Paper
                        elevation={4}
                        sx={{
                            p: 3,
                            borderRadius: 3,
                            backgroundColor: "#ffffff",
                            boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.06)",
                        }}
                    >
                        <Typography variant="h6" fontWeight={600} gutterBottom>
                            Retention Overview
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <RetentionChart />
                    </Paper>
                </Grid>

                {/* Payroll Distribution Chart */}
                <Grid item xs={12} md={6}>
                    <Paper
                        elevation={4}
                        sx={{
                            p: 3,
                            borderRadius: 3,
                            backgroundColor: "#ffffff",
                            boxShadow: "0px 4px 12px rgba(0, 0, 0, 0.06)",
                        }}
                    >
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
