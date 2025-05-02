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
        status: ''
    });

    useEffect(() => {
        if (isEdit) {
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
                status: ''
            });
        }
    }, [payroll]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
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
            alert('❌ Failed to save payroll.');
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
                            <MenuItem value="Paid">Paid</MenuItem>
                            <MenuItem value="Pending">Pending</MenuItem>
                            <MenuItem value="On Hold">On Hold</MenuItem>
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
                <Button onClick={handleSubmit} variant="contained">
                    {isEdit ? 'Update' : 'Create'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default PayrollForm;
