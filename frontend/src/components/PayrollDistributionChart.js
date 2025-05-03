import React, { useEffect, useState } from "react";
import { Pie } from "react-chartjs-2";
import { Card, CardContent, Typography } from "@mui/material";
import DashboardService from "../services/dashboardService";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

const PayrollDistributionChart = () => {
    const [salaryRanges, setSalaryRanges] = useState({});

    useEffect(() => {
        DashboardService.getPayrollDistribution()
            .then((response) => setSalaryRanges(response.data))
            .catch((err) => console.error("Error loading payroll distribution", err));
    }, []);

    // Prepare data for the pie chart based on salary ranges
    const chartData = {
        labels: Object.keys(salaryRanges), // ["Below 1k", "1k-2k", "2k-3k", "3k-5k", "5k+"]
        datasets: [
            {
                data: Object.values(salaryRanges), // e.g., [10000, 15000, 12000, 18000, 25000]
                backgroundColor: ["#ff6384", "#36a2eb", "#ffcd56", "#4bc0c0", "#9966ff"], // Different colors for each range
            },
        ],
    };

    return (
        <Card>
            <CardContent>
                <Typography variant="h6" gutterBottom>
                    Payroll Distribution by Salary Range
                </Typography>
                <Pie data={chartData} />
            </CardContent>
        </Card>
    );
};

export default PayrollDistributionChart;
