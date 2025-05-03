import React from "react";
import { Grid, Box, Paper, Typography, Container, Divider } from "@mui/material";
import DashboardKpi from "../components/DashboardKpi";
import RetentionChart from "../components/RetentionChart";
import PayrollDistributionChart from "../components/PayrollDistributionChart";

const Dashboard = () => {
    const username = localStorage.getItem("username") || "User";

    return (
        <Container maxWidth="xl" sx={{ py: 4 }}>
            {/* Header Section */}
            <Box mb={4}>
                <Typography variant="h4" fontWeight={700} color="primary.main">
                    Hello, {username}
                </Typography>
                <Typography variant="body1" color="text.secondary" mt={1}>
                    Welcome to your dashboard. Track employee stats, contracts, payrolls, and more.
                </Typography>
            </Box>

            <Grid container spacing={4}>
                {/* KPI Cards (Top Row) */}
                <Grid item xs={12} sm={6} md={4}>
                    <Paper
                        elevation={6}
                        sx={{
                            p: 3,
                            borderRadius: 3,
                            backgroundColor: "#f9f9f9",
                            boxShadow: "0px 8px 24px rgba(0, 0, 0, 0.12)",
                        }}
                    >
                        <DashboardKpi />
                    </Paper>
                </Grid>

                {/* Payroll Distribution Chart */}
                <Grid item xs={12} sm={6} md={4}>
                    <Paper
                        elevation={6}
                        sx={{
                            p: 3,
                            borderRadius: 3,
                            backgroundColor: "#ffffff",
                            boxShadow: "0px 8px 24px rgba(0, 0, 0, 0.12)",
                        }}
                    >
                        <Typography variant="h6" fontWeight={600} color="primary.main" gutterBottom>
                            Payroll Distribution
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <PayrollDistributionChart />
                    </Paper>
                </Grid>

                {/* Retention Overview Chart */}
                <Grid item xs={12} sm={6} md={4}>
                    <Paper
                        elevation={6}
                        sx={{
                            p: 3,
                            borderRadius: 3,
                            backgroundColor: "#ffffff",
                            boxShadow: "0px 8px 24px rgba(0, 0, 0, 0.12)",
                        }}
                    >
                        <Typography variant="h6" fontWeight={600} color="primary.main" gutterBottom>
                            Retention Overview
                        </Typography>
                        <Divider sx={{ mb: 2 }} />
                        <RetentionChart />
                    </Paper>
                </Grid>
            </Grid>

            {/* Additional Content Section (if necessary) */}
            <Grid container spacing={4} sx={{ mt: 4 }}>
                {/* Example for future additions */}
                {/* <Grid item xs={12} sm={6} md={4}>
                    <Paper sx={{ p: 3, borderRadius: 3, boxShadow: 3 }}>
                        {/* Additional content like charts, tables, etc. */}
                {/*</Paper>
                </Grid> */}
            </Grid>
        </Container>
    );
};

export default Dashboard;
