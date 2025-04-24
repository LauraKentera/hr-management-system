import React, { useState } from 'react';
import EmployeeGrowthChart from '../components/EmployeeGrowthChart';
import EmployeeStatusChart from '../components/EmployeeStatusChart';
import EmployeeTable from '../components/EmployeeTable';
import KPICard from '../components/KPICard';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header (Topbar) component
import '../styles/Dashboard.css';  // Ensure Dashboard CSS is applied
import { Box, Grid, Typography, Card, CardContent, Paper } from '@mui/material';

const EmployeeView = () => {
  const [employeeData] = useState({
    totalEmployees: 120,
    newHiresThisMonth: 15,
    turnoverRate: 5,
    engagedEmployees: 85,
  });

  // Dummy employee data for the table
  const [employees] = useState([
    { id: 1, name: 'John Doe', email: 'john.doe@example.com', department: 'Engineering', status: 'Active' },
    { id: 2, name: 'Jane Smith', email: 'jane.smith@example.com', department: 'Marketing', status: 'On Leave' },
    { id: 3, name: 'Alex Johnson', email: 'alex.johnson@example.com', department: 'HR', status: 'Active' },
    { id: 4, name: 'Emily Davis', email: 'emily.davis@example.com', department: 'Sales', status: 'Inactive' },
    { id: 5, name: 'Michael Brown', email: 'michael.brown@example.com', department: 'IT', status: 'Active' },
  ]);

  return (
      <div className="dashboard-page">
        {/* Sidebar and Topbar */}
        <Sidebar />
        <Header />

        {/* Main Content Area */}
        <div className="dashboard-container">
          <Typography variant="h4" gutterBottom>
            Employee Overview
          </Typography>

          {/* KPI Cards */}

          <Grid container spacing={4}>
          <Grid container spacing={4}>
            <Grid item xs={12} sm={6} md={3}>
              <KPICard title="Total Employees" value={employeeData.totalEmployees} color="primary" />
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <KPICard title="New Hires This Month" value={employeeData.newHiresThisMonth} color="success" />
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <KPICard title="Turnover Rate (%)" value={employeeData.turnoverRate} color="error" />
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <KPICard title="Engaged Employees (%)" value={employeeData.engagedEmployees} color="secondary" />
            </Grid>
          </Grid>


          {/* Grid Layout for Charts and KPI Cards */}
          <Grid container spacing={4}>
            {/* Employee Growth Chart */}
            <Grid item xs={12} sm={6} md={6}>
              <Card sx={{ boxShadow: 3, borderRadius: 2 }}>
                <CardContent>
                  <Typography variant="h6" gutterBottom>
                    Employee Growth
                  </Typography>
                  <EmployeeGrowthChart />
                </CardContent>
              </Card>
            </Grid>

            {/* Employee Status Chart */}
            <Grid item xs={12} sm={6} md={6}>
              <Card sx={{ boxShadow: 3, borderRadius: 2 }}>
                <CardContent>
                  <Typography variant="h6" gutterBottom>
                    Employee Status
                  </Typography>
                  <EmployeeStatusChart />
                </CardContent>
              </Card>
            </Grid>


          </Grid>
          </Grid>

          {/* Employee Table */}
          <Box sx={{ mt: 4 }}>
            <Typography variant="h6" gutterBottom>
              Employee List
            </Typography>
            <Paper sx={{ boxShadow: 3, borderRadius: 2 }}>
              <EmployeeTable employees={employees} />
            </Paper>
          </Box>
        </div>
      </div>
  );
};

export default EmployeeView;
