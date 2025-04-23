import React, { useEffect, useState } from 'react';
import { Container, Grid, Typography, Paper, Box } from '@mui/material';
import AttendanceGraph from '../components/AttendanceGraph'; // Import the Attendance Graph component
import Sidebar from '../components/Sidebar/Sidebar'; // Import Sidebar component
import Topbar from '../components/Topbar/Topbar'; // Import Header component
import '../styles/Dashboard.css'; // Import custom CSS for styling

const DashboardPage = () => {
    // State to hold the dashboard data (dummy data for now)
    const [dashboardData, setDashboardData] = useState({
        attendance: 0,
        lateArrivals: 0,
        absents: 0,
        leaveApplied: 0,
    });

    // Simulate an API call with dummy data (useEffect just for now)
    useEffect(() => {
        // Dummy data to simulate backend response
        setDashboardData({
            attendance: 95,
            lateArrivals: 5,
            absents: 3,
            leaveApplied: 2,
        });
    }, []);

    return (
        <div className="dashboard-page">
            {/* Sidebar and Header */}
            <Sidebar />
            <Topbar />

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
                        <Grid item xs={3}>
                            <Paper className="stat-card">
                                <Typography variant="h6">Attendance</Typography>
                                <Typography variant="h4">{dashboardData.attendance}%</Typography>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className="stat-card">
                                <Typography variant="h6">Late Arrivals</Typography>
                                <Typography variant="h4">{dashboardData.lateArrivals}</Typography>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className="stat-card">
                                <Typography variant="h6">Absents</Typography>
                                <Typography variant="h4">{dashboardData.absents}</Typography>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className="stat-card">
                                <Typography variant="h6">Leave Applied</Typography>
                                <Typography variant="h4">{dashboardData.leaveApplied}</Typography>
                            </Paper>
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

                {/* Additional Sections (Optional): Leave Requests, Early Risers, etc. */}
            </Container>
        </div>
    );
};

export default DashboardPage;
