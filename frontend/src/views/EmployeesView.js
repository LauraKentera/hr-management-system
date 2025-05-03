import React, { useEffect, useState } from 'react';
import {
    Container, Typography, Button, Snackbar, Table, TableHead, TableRow, TableCell, TableBody,
    Dialog, DialogTitle, DialogContent, DialogActions, TextField, MenuItem, Grid, CircularProgress
} from '@mui/material';
import axios from 'axios';

// Initial form state
const initialFormData = () => ({
    pin: '', firstName: '', lastName: '', birthDate: '', dateOfHire: '', phoneNumber: '', email: '', address: '',
    gender: 'Male', nationalityId: '', departmentId: '', positionId: '', employmentStatus: 'Active',
    emergencyContactName: '', emergencyContactPhone: '', maritalStatus: 'Single', employmentType: 'Full-Time',
    managerId: null, taxId: '', bankAccountNumber: ''
});

const EmployeesView = () => {
    // State hooks
    const [employees, setEmployees] = useState([]);
    const [openForm, setOpenForm] = useState(false);
    const [formData, setFormData] = useState(initialFormData());
    const [dropdowns, setDropdowns] = useState({
        nationalities: [],
        departments: [],
        positions: []
    });
    const [snackbar, setSnackbar] = useState({ open: false, message: '' });
    const [loading, setLoading] = useState(true);

    // Load data on mount
    useEffect(() => {
        fetchEmployees();
        fetchDropdownData();
    }, []);

    // Fetch employee list
    const fetchEmployees = async () => {
        try {
            const res = await axios.get('/api/employees');
            setEmployees(res.data);
        } catch {
            showSnackbar('Failed to fetch employees');
        } finally {
            setLoading(false);
        }
    };

    // Fetch dropdown options
    const fetchDropdownData = async () => {
        try {
            const [nat, dep, pos] = await Promise.all([
                axios.get('/api/nationalities'),
                axios.get('/api/departments'),
                axios.get('/api/positions')
            ]);
            setDropdowns({
                nationalities: nat.data,
                departments: dep.data,
                positions: pos.data
            });
        } catch {
            showSnackbar('Failed to load dropdowns');
        }
    };

    // Snackbar handler
    const showSnackbar = (message) => setSnackbar({ open: true, message });

    // Form input change handler
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    // Submit form
    const handleSubmit = async () => {
        try {
            await axios.post('/api/employees', formData);
            showSnackbar('✅ Employee added successfully');
            setOpenForm(false);
            setFormData(initialFormData());
            fetchEmployees();
        } catch {
            showSnackbar('❌ Failed to save employee');
        }
    };

    return (
        <Container>
            {/* Header */}
            <Typography variant="h3" gutterBottom>
                Employee Directory
            </Typography>

            <Button
                variant="contained"
                onClick={() => setOpenForm(true)}
                sx={{ mb: 3, fontSize: '1rem', padding: '10px 20px' }}
            >
                ➕ Add Employee
            </Button>

            {/* Loading Indicator */}
            {loading ? (
                <CircularProgress />
            ) : (
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell><strong>ID</strong></TableCell>
                            <TableCell><strong>PIN</strong></TableCell>
                            <TableCell><strong>Name</strong></TableCell>
                            <TableCell><strong>Department</strong></TableCell>
                            <TableCell><strong>Position</strong></TableCell>
                            <TableCell><strong>Status</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {employees.map((emp) => (
                            <TableRow key={emp.id}>
                                <TableCell>{emp.id}</TableCell>
                                <TableCell>{emp.pin}</TableCell>
                                <TableCell>{emp.firstName} {emp.lastName}</TableCell>
                                <TableCell>{emp.department?.name}</TableCell>
                                <TableCell>{emp.position?.title}</TableCell>
                                <TableCell>{emp.employmentStatus}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}

            {/* Dialog Form */}
            <Dialog open={openForm} onClose={() => setOpenForm(false)} maxWidth="md" fullWidth>
                <DialogTitle>Add New Employee</DialogTitle>
                <DialogContent>
                    <Grid container spacing={2} sx={{ mt: 1 }}>
                        {[
                            { label: 'PIN', name: 'pin' },
                            { label: 'First Name', name: 'firstName' },
                            { label: 'Last Name', name: 'lastName' },
                            { label: 'Birth Date', name: 'birthDate', type: 'date' },
                            { label: 'Date of Hire', name: 'dateOfHire', type: 'date' },
                            { label: 'Phone', name: 'phoneNumber' },
                            { label: 'Email', name: 'email' },
                            { label: 'Address', name: 'address' },
                            { label: 'Emergency Contact Name', name: 'emergencyContactName' },
                            { label: 'Emergency Contact Phone', name: 'emergencyContactPhone' },
                            { label: 'Tax ID', name: 'taxId' },
                            { label: 'Bank Account Number', name: 'bankAccountNumber' }
                        ].map(({ label, name, type = 'text' }) => (
                            <Grid item xs={6} key={name}>
                                <TextField
                                    label={label}
                                    name={name}
                                    type={type}
                                    fullWidth
                                    required={label !== 'Bank Account Number'}
                                    value={formData[name]}
                                    onChange={handleChange}
                                    InputLabelProps={type === 'date' ? { shrink: true } : {}}
                                />
                            </Grid>
                        ))}

                        {/* Select dropdowns */}
                        <Grid item xs={6}>
                            <TextField select label="Gender" name="gender" fullWidth value={formData.gender} onChange={handleChange}>
                                {['Male', 'Female', 'Other'].map(opt => (
                                    <MenuItem key={opt} value={opt}>{opt}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        <Grid item xs={6}>
                            <TextField select label="Marital Status" name="maritalStatus" fullWidth value={formData.maritalStatus} onChange={handleChange}>
                                {['Single', 'Married', 'Divorced', 'Widowed'].map(opt => (
                                    <MenuItem key={opt} value={opt}>{opt}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        <Grid item xs={6}>
                            <TextField select label="Employment Status" name="employmentStatus" fullWidth value={formData.employmentStatus} onChange={handleChange}>
                                {['Active', 'On Leave', 'Terminated'].map(opt => (
                                    <MenuItem key={opt} value={opt}>{opt}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        <Grid item xs={6}>
                            <TextField select label="Employment Type" name="employmentType" fullWidth value={formData.employmentType} onChange={handleChange}>
                                {['Full-Time', 'Part-Time', 'Contractor'].map(opt => (
                                    <MenuItem key={opt} value={opt}>{opt}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        <Grid item xs={6}>
                            <TextField select label="Nationality" name="nationalityId" fullWidth value={formData.nationalityId} onChange={handleChange}>
                                {dropdowns.nationalities.map(n => (
                                    <MenuItem key={n.nationalityId} value={n.nationalityId}>{n.name}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        <Grid item xs={6}>
                            <TextField select label="Department" name="departmentId" fullWidth value={formData.departmentId} onChange={handleChange}>
                                {dropdowns.departments.map(d => (
                                    <MenuItem key={d.departmentId} value={d.departmentId}>{d.name}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>

                        <Grid item xs={6}>
                            <TextField select label="Position" name="positionId" fullWidth value={formData.positionId} onChange={handleChange}>
                                {dropdowns.positions.map(p => (
                                    <MenuItem key={p.positionId} value={p.positionId}>{p.title}</MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenForm(false)}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">Save</Button>
                </DialogActions>
            </Dialog>

            {/* Snackbar feedback */}
            <Snackbar
                open={snackbar.open}
                autoHideDuration={3000}
                message={snackbar.message}
                onClose={() => setSnackbar({ open: false, message: '' })}
            />
        </Container>
    );
};

export default EmployeesView;
