import React, { useEffect, useState } from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    TextField, Button, Grid, MenuItem
} from '@mui/material';
import axios from 'axios';

const PayrollForm = ({ open, onClose, refreshPayrolls, payroll }) => {
    const isEdit = Boolean(payroll);

    const [formData, setFormData] = useState({
        employeeId: '',
        periodStart: '',
        periodEnd: '',
        baseSalary: '',
        bonus: '',
        deductions: '',
        netPay: '',
        paymentDate: '',
        status: 'Pending'
    });

    useEffect(() => {
        if (isEdit && payroll) {
            setFormData({ ...payroll });
        } else {
            setFormData({
                employeeId: '',
                periodStart: '',
                periodEnd: '',
                baseSalary: '',
                bonus: '',
                deductions: '',
                netPay: '',
                paymentDate: '',
                status: 'Pending'
            });
        }
    }, [payroll, isEdit]);

    const handleChange = (e) => {
        const { name, value } = e.target;
    
        const updated = {
            ...formData,
            [name]: name === 'baseSalary' || name === 'bonus' || name === 'deductions'
                ? parseFloat(value || 0)
                : name === 'employeeId'
                ? parseInt(value || 0)
                : value
        };
    
        // Automatically recalculate net pay
        const base = parseFloat(updated.baseSalary || 0);
        const bonus = parseFloat(updated.bonus || 0);
        const deductions = parseFloat(updated.deductions || 0);
        updated.netPay = base + bonus - deductions;
    
        setFormData(updated);
    };
    

    const handleSubmit = async () => {
        try {
            if (isEdit) {
                await axios.put(`/api/payrolls/${payroll.payrollId}`, formData);
            } else {
                await axios.post('/api/payrolls', formData);
            }
            refreshPayrolls();
            onClose();
        } catch (err) {
            const msg = err.response?.data?.message || 'Failed to save payroll.';
            alert('❌ ' + msg);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
            <DialogTitle>{isEdit ? 'Edit Payroll' : 'Add Payroll'}</DialogTitle>
            <DialogContent>
                <Grid container spacing={2} sx={{ mt: 1 }}>
                    <Grid item xs={6}>
                        <TextField
                            label="Employee ID"
                            name="employeeId"
                            value={formData.employeeId}
                            onChange={handleChange}
                            fullWidth
                            type="number"
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Status"
                            name="status"
                            value={formData.status}
                            onChange={handleChange}
                            fullWidth
                            select
                        >
                            <MenuItem value="Pending">Pending</MenuItem>
                            <MenuItem value="Processed">Processed</MenuItem>
                            <MenuItem value="Paid">Paid</MenuItem>
                        </TextField>
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Period Start"
                            name="periodStart"
                            type="date"
                            value={formData.periodStart}
                            onChange={handleChange}
                            InputLabelProps={{ shrink: true }}
                            fullWidth
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Period End"
                            name="periodEnd"
                            type="date"
                            value={formData.periodEnd}
                            onChange={handleChange}
                            InputLabelProps={{ shrink: true }}
                            fullWidth
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Base Salary (€)"
                            name="baseSalary"
                            type="number"
                            value={formData.baseSalary}
                            onChange={handleChange}
                            fullWidth
                            required
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Bonus (€)"
                            name="bonus"
                            type="number"
                            value={formData.bonus}
                            onChange={handleChange}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Deductions (€)"
                            name="deductions"
                            type="number"
                            value={formData.deductions}
                            onChange={handleChange}
                            fullWidth
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            label="Net Pay (€)"
                            name="netPay"
                            type="number"
                            value={formData.netPay}
                            onChange={handleChange}
                            fullWidth
                            required
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="Payment Date"
                            name="paymentDate"
                            type="date"
                            value={formData.paymentDate}
                            onChange={handleChange}
                            InputLabelProps={{ shrink: true }}
                            fullWidth
                        />
                    </Grid>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button variant="contained" onClick={handleSubmit}>
                    {isEdit ? 'Update' : 'Create'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default PayrollForm;
