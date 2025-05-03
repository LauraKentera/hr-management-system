import React, { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import { Card, CardContent, Typography } from "@mui/material";
import DashboardService from "../services/dashboardService";
import {
    Chart as ChartJS,
    LineElement,
    PointElement,
    CategoryScale,
    LinearScale,
    Title,
    Tooltip,
    Legend,
} from "chart.js";

ChartJS.register(LineElement, PointElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

const RetentionChart = () => {
    const [retention, setRetention] = useState(0);

    useEffect(() => {
        DashboardService.getRetentionRate()
            .then((response) => setRetention(response.data))
            .catch((err) => console.error("Error loading retention rate", err));
    }, []);

    const chartData = {
        labels: ["Q1", "Q2", "Q3", "Q4"], // Placeholder quarters
        datasets: [
            {
                label: "Retention Rate",
                data: [retention - 10, retention - 5, retention, retention + 2], // Simulated trend
                fill: false,
                borderColor: "#4caf50",
                tension: 0.3,
            },
        ],
    };

    const chartOptions = {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                max: 100,
            },
        },
    };

    return (
        <Card>
            <CardContent>
                <Typography variant="h6" gutterBottom>
                    Employee Retention Rate (Trend)
                </Typography>
                <Line data={chartData} options={chartOptions} />
            </CardContent>
        </Card>
    );
};

export default RetentionChart;
