import React, { useEffect, useState } from "react";
import { Card, CardContent, Typography, Grid } from "@mui/material";
import DashboardService from "../services/dashboardService"; // optional, replace if still using axios

const KpiCard = ({ title, value }) => (
    <Card>
        <CardContent>
            <Typography variant="subtitle2" color="textSecondary">
                {title}
            </Typography>
            <Typography variant="h5" fontWeight="bold">
                {value}
            </Typography>
        </CardContent>
    </Card>
);

const DashboardKpi = () => {
    const [data, setData] = useState({
        totalEmployees: 0,
        activeContracts: 0,
        averageSalary: 0,
    });

    useEffect(() => {
        DashboardService.getOverview() // or axios.get("/api/dashboard/overview")
            .then((response) => setData(response.data))
            .catch((error) => console.error("Failed to load dashboard data", error));
    }, []);

    return (
        <Grid container spacing={2}>
            <Grid item xs={12} sm={4}>
                <KpiCard title="Total Employees" value={data.totalEmployees} />
            </Grid>
            <Grid item xs={12} sm={4}>
                <KpiCard title="Active Contracts" value={data.activeContracts} />
            </Grid>
            <Grid item xs={12} sm={4}>
                <KpiCard title="Average Salary" value={`$${data.averageSalary?.toFixed(2)}`} />
            </Grid>
        </Grid>
    );
};

export default DashboardKpi;
