import React from "react";
import { Grid, Container } from "@mui/material";
import DashboardKpi from "../components/DashboardKpi";
import RetentionChart from "../components/RetentionChart"; // <-- use new line chart
import PayrollDistributionChart from "../components/PayrollDistributionChart";
import "../styles/Dashboard.css";

const Dashboard = () => {
    const username = localStorage.getItem("username") || "User";

    return (
        <Container maxWidth="lg" className="dashboard-container">
            <div className="dashboard-header">
                <h2>Hello, {username}</h2>
                <p>Welcome to your dashboard. Here you can track employee stats, contracts, payrolls, and more.</p>
            </div>

            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <DashboardKpi />
                </Grid>

                <Grid item xs={12} md={6}>
                    <RetentionChart />
                </Grid>

                <Grid item xs={12} md={6}>
                    <PayrollDistributionChart />
                </Grid>
            </Grid>
        </Container>
    );
};

export default Dashboard;
