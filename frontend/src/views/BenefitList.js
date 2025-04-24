import React, { useState, useEffect } from 'react';
import { Container, Grid, Typography, Box, Button, Card, CardContent, CardActions } from '@mui/material';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header component
import BenefitForm from '../components/BenefitForm';  // Import Benefit Form component
import '../styles/Dashboard.css';  // Use the Dashboard CSS for styling
import '../styles/BenefitList.css';  // Use the Dashboard CSS for styling

function BenefitList() {
    const [benefits, setBenefits] = useState([]);
    const [showForm, setShowForm] = useState(false);

    // Dummy data for benefits
    const loadBenefits = () => {
        const dummyBenefits = [
            { id: 1, name: 'Health Insurance', amount: 500 },
            { id: 2, name: 'Dental Coverage', amount: 50 },
            { id: 3, name: 'Retirement Plan', amount: 200 },
        ];
        setBenefits(dummyBenefits);
    };

    useEffect(() => {
        loadBenefits();
    }, []);

    return (
        <div className="dashboard-page">
            {/* Sidebar and Header */}
            <Sidebar />
            <Header />

            {/* Main Content Area */}
            <Container className="dashboard-container">
                {/* Overview Section */}
                {/*<Box className="overview-box" sx={{ marginBottom: 3 }}>*/}
                <Box  sx={{ marginBottom: 3 }}>
                    <Typography variant="h4" gutterBottom>
                        Benefits List
                    </Typography>
                    <Typography variant="h6" paragraph>
                        Here's a list of employee benefits
                    </Typography>

                    {/* Add Benefit Button */}
                    <Button variant="contained" color="primary" onClick={() => setShowForm(true)} sx={{ marginBottom: 2 }}>
                        + Add Benefit
                    </Button>

                    {/* Benefit Form Popup */}
                    {showForm && (
                        <BenefitForm
                            onClose={() => setShowForm(false)}
                            onSubmitSuccess={loadBenefits} // Reload benefits after form submission
                        />
                    )}

                    {/* Benefit Cards */}
                    <Grid container spacing={3}>
                        {benefits.map((benefit) => (
                            <Grid item xs={12} sm={6} md={4} key={benefit.id}>
                                <Card sx={{ borderRadius: 2, boxShadow: 3, padding: 2 }}>
                                    <CardContent>
                                        <Typography variant="h6">{benefit.name}</Typography>
                                        <Typography variant="body2" color="textSecondary">
                                            ${benefit.amount}
                                        </Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button size="small" color="primary" onClick={() => console.log('Edit clicked')}>
                                            Edit
                                        </Button>
                                    </CardActions>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            </Container>
        </div>
    );
}

export default BenefitList;
