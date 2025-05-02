import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Typography, Paper, Grid, Button } from '@mui/material';
import Sidebar from '../components/Sidebar';
import Header from '../components/Topbar';
import ApiEndpoints from '../api/ApiEndpoints';
import '../styles/Dashboard.css';

const EmployeeDetailsView = () => {
  const { id } = useParams();
  const [employee, setEmployee] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(`${ApiEndpoints.employee.getAll}/${id}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error(`Failed to fetch employee details: ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        setEmployee(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [id]);

  if (loading) {
    return <Typography>Loading...</Typography>;
  }

  if (error) {
    return <Typography color="error">{error}</Typography>;
  }

  if (!employee) {
    return <Typography>No employee found.</Typography>;
  }

  return (
    <div className="dashboard-page">
      <Sidebar />
      <Header />
      <div className="dashboard-container">
        <Typography variant="h4" gutterBottom>
          Employee Details
        </Typography>
        <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <Typography variant="h6">Name: {`${employee.firstName} ${employee.lastName}`}</Typography>
              <Typography>Email: {employee.email}</Typography>
              <Typography>Department: {employee.department?.name || 'N/A'}</Typography>
              <Typography>Status: {employee.employmentStatus}</Typography>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Typography variant="h6">Additional Details</Typography>
              <Typography>Phone: {employee.phoneNumber || 'N/A'}</Typography>
              <Typography>Address: {employee.address || 'N/A'}</Typography>
              <Typography>Manager: {employee.manager?.name || 'N/A'}</Typography>
            </Grid>
          </Grid>
          <Button
            variant="contained"
            color="secondary"
            sx={{ mt: 2 }}
            onClick={() => window.history.back()}
          >
            Back
          </Button>
        </Paper>
      </div>
    </div>
  );
};

export default EmployeeDetailsView;