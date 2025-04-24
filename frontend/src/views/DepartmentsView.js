import React, { useState } from 'react';
import DepartmentForm from '../components/DepartmentForm';  // Department form component
import PositionTree from '../components/PositionTree';  // Position Tree component
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header (Topbar) component
import '../styles/Dashboard.css';  // Ensure Dashboard CSS is applied
import { Box, Button, Grid, Typography, Card, CardContent, Paper } from '@mui/material';

const DepartmentsView = () => {
    const [showForm, setShowForm] = useState(false); // To control DepartmentForm visibility
    const [selectedDepartment, setSelectedDepartment] = useState(null); // For editing department

    // Dummy departments data
    const departments = [
        { id: 1, name: 'Engineering', manager: { id: 1, firstName: 'John', lastName: 'Doe' } },
        { id: 2, name: 'Marketing', manager: { id: 2, firstName: 'Jane', lastName: 'Smith' } },
        { id: 3, name: 'Sales', manager: { id: 3, firstName: 'Michael', lastName: 'Brown' } },
    ];

    return (
        <div className="dashboard-page">
            {/* Sidebar and Topbar */}
            <Sidebar />
            <Header />

            {/* Main Content Area */}
            <div className="dashboard-container">
                <Typography variant="h4" gutterBottom>
                    Departments Overview
                </Typography>

                {/* Buttons to toggle the form */}
                <Box sx={{ mb: 4 }}>
                    <Button variant="contained" color="primary" onClick={() => setShowForm(true)} sx={{ mr: 2 }}>
                        Add New Department
                    </Button>
                    {selectedDepartment && (
                        <Button
                            variant="outlined"
                            color="primary"
                            onClick={() => setShowForm(true)}
                        >
                            Edit Department
                        </Button>
                    )}
                </Box>

                {/* Department Form */}
                {showForm && (
                    <DepartmentForm
                        onClose={() => setShowForm(false)}
                        initialData={selectedDepartment}
                        onSave={() => setSelectedDepartment(null)} // Reset selection after form success
                    />
                )}

                {/* Grid layout for Department Cards */}
                <Grid container spacing={4} sx={{ mb: 4 }}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
                            <Typography variant="h6" gutterBottom>
                                Existing Departments
                            </Typography>
                            <Grid container spacing={2}>
                                {departments.map(department => (
                                    <Grid item xs={12} sm={6} md={4} key={department.id}>
                                        <Card sx={{ boxShadow: 3, borderRadius: 2 }}>
                                            <CardContent>
                                                <Typography variant="h6">{department.name}</Typography>
                                                <Typography variant="body2" color="textSecondary">
                                                    Manager: {department.manager.firstName} {department.manager.lastName}
                                                </Typography>
                                                <Button
                                                    size="small"
                                                    color="primary"
                                                    onClick={() => { setSelectedDepartment(department); setShowForm(true); }}
                                                >
                                                    Edit
                                                </Button>
                                            </CardContent>
                                        </Card>
                                    </Grid>
                                ))}
                            </Grid>
                        </Paper>
                    </Grid>
                </Grid>

                {/* Position Tree */}
                <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
                    <Typography variant="h6" gutterBottom>
                        Position Tree
                    </Typography>
                    <PositionTree />
                </Paper>
            </div>
        </div>
    );
};

export default DepartmentsView;
