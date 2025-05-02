import React, { useState } from 'react';
import { Grid, Button, TextField, Snackbar } from '@mui/material';
import PayrollService from '../services/PayrollService';

const PayrollGenerator = ({ refreshPayrolls }) => {
    const [employeeId, setEmployeeId] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [snackbar, setSnackbar] = useState({ open: false, message: '' });

    const handleGenerate = async () => {
        try {
            await PayrollService.generate({ employeeId, startDate, endDate });
            setSnackbar({ open: true, message: '✅ Payroll generated!' });
            refreshPayrolls();
        } catch (err) {
            setSnackbar({ open: true, message: '❌ Failed to generate payroll.' });
        }
    };

    return (
        <Grid container spacing={2} sx={{ my: 2 }}>
            <Grid item xs={3}>
                <TextField
                    label="Employee ID"
                    type="number"
                    fullWidth
                    value={employeeId}
                    onChange={(e) => setEmployeeId(e.target.value)}
                />
            </Grid>
            <Grid item xs={3}>
                <TextField
                    label="Start Date"
                    type="date"
                    fullWidth
                    InputLabelProps={{ shrink: true }}
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                />
            </Grid>
            <Grid item xs={3}>
                <TextField
                    label="End Date"
                    type="date"
                    fullWidth
                    InputLabelProps={{ shrink: true }}
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                />
            </Grid>
            <Grid item xs={3}>
                <Button variant="outlined" fullWidth onClick={handleGenerate}>
                    Generate Payroll
                </Button>
            </Grid>

            <Snackbar
                open={snackbar.open}
                autoHideDuration={3000}
                message={snackbar.message}
                onClose={() => setSnackbar({ open: false, message: '' })}
            />
        </Grid>
    );
};

export default PayrollGenerator;
