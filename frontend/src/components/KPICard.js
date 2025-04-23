import React from 'react';
import { Paper, Typography, Box } from '@mui/material';

// Reusable KPI Card Component
const KPICard = ({ title, value, color }) => {
    return (
        <Paper
            style={{
                padding: '20px',
                backgroundColor: '#fff',
                boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)',
                borderRadius: '8px'
            }}
        >
            <Box>
                <Typography variant="h6">{title}</Typography>
                <Typography variant="h4" style={{ color: color }}>
                    {value}
                </Typography>
            </Box>
        </Paper>
    );
};

export default KPICard;
