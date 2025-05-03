import React, { useEffect, useState } from 'react';
import { Container, Typography, Button, CircularProgress, Snackbar, Table, TableBody, TableCell, TableHead, TableRow, Paper } from '@mui/material';
import PayrollForm from './PayrollForm';
import PayrollService from '../services/PayrollService';
import { Box } from '@mui/material';
import Header from '../components/Topbar';


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
        <Container sx={{ paddingTop: 4 }}>
            <Header /> {/* Add the Header (Topbar) component */}
            <Typography variant="h4" gutterBottom sx={{ fontWeight: 600, color: '#004e92' }}>
                Salaries
            </Typography>

            <Button
                variant="contained"
                onClick={() => setOpenForm(true)}
                sx={{
                    mb: 2,
                    backgroundColor: '#0077b6',
                    '&:hover': { backgroundColor: '#005f8a' },
                    textTransform: 'none'
                }}
            >
                Add Payroll
            </Button>


            {loading ? (
                <Box display="flex" justifyContent="center" p={4}>
                    <CircularProgress />
                </Box>
            ) : (
                <Paper sx={{ p: 2, boxShadow: 2 }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>ID</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Employee ID</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Start</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>End</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Net Pay</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Status</TableCell>
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
                </Paper>
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
