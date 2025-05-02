import React, { useEffect, useState } from 'react';
import { Container, Typography, Button, CircularProgress, Snackbar, Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material';
import PayrollForm from './PayrollForm';
import PayrollGenerator from './PayrollGenerator';
import PayrollService from '../services/PayrollService';

const PayrollPage = () => {
    const [payrolls, setPayrolls] = useState([]);
    const [loading, setLoading] = useState(true);
    const [openForm, setOpenForm] = useState(false);
    const [selectedPayroll, setSelectedPayroll] = useState(null);
    const [snackbar, setSnackbar] = useState({ open: false, message: '' });

    const fetchPayrolls = async () => {
        try {
            const response = await PayrollService.getAll();
            setPayrolls(response.data);
        } catch (error) {
            setSnackbar({ open: true, message: 'Failed to fetch payrolls' });
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPayrolls();
    }, []);

    return (
        <Container>
            <Typography variant="h4" gutterBottom>Salaries</Typography>

            <Button variant="contained" onClick={() => setOpenForm(true)} sx={{ mb: 2 }}>
                Add Payroll
            </Button>

            <PayrollGenerator refreshPayrolls={fetchPayrolls} />

            {loading ? (
                <CircularProgress />
            ) : (
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Employee ID</TableCell>
                            <TableCell>Start</TableCell>
                            <TableCell>End</TableCell>
                            <TableCell>Net Pay</TableCell>
                            <TableCell>Status</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {payrolls.map((p) => (
                            <TableRow key={p.payrollId}>
                                <TableCell>{p.payrollId}</TableCell>
                                <TableCell>{p.employeeId}</TableCell>
                                <TableCell>{p.periodStart}</TableCell>
                                <TableCell>{p.periodEnd}</TableCell>
                                <TableCell>{p.netPay} €</TableCell>
                                <TableCell>{p.status}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}

            <PayrollForm
                open={openForm}
                onClose={() => setOpenForm(false)}
                refreshPayrolls={fetchPayrolls}
                payroll={selectedPayroll}
            />

            <Snackbar
                open={snackbar.open}
                autoHideDuration={3000}
                message={snackbar.message}
                onClose={() => setSnackbar({ open: false, message: '' })}
            />
        </Container>
    );
};

export default PayrollPage;