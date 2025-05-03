// EmployeesView.js (Fixed)
import React, { useEffect, useState } from 'react';
import {
    Container, Typography, Button, Snackbar, Table, TableHead, TableRow, TableCell, TableBody,
    Dialog, DialogTitle, DialogContent, DialogActions, TextField, MenuItem, Grid, CircularProgress
} from '@mui/material';
import axios from 'axios';

const initialFormData = () => ({
    pin: '', firstName: '', lastName: '', birthDate: '', dateOfHire: '',
    phoneNumber: '', email: '', address: '', gender: 'Male',
    nationalityId: '', departmentId: '', positionId: '', employmentStatus: 'Active',
    emergencyContactName: '', emergencyContactPhone: '', maritalStatus: 'Single',
    employmentType: 'Full-Time', managerId: '', taxId: '', bankAccountNumber: ''
});

const EmployeesView = () => {
    const [employees, setEmployees] = useState([]);
    const [openForm, setOpenForm] = useState(false);
    const [formData, setFormData] = useState(initialFormData());
    const [dropdowns, setDropdowns] = useState({ nationalities: [], departments: [], positions: [] });
    const [snackbar, setSnackbar] = useState({ open: false, message: '' });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchEmployees();
        fetchDropdownData();
    }, []);

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

    const fetchDropdownData = async () => {
        try {
            const [nat, dep, pos] = await Promise.all([
                axios.get('/api/nationalities'),
                axios.get('/api/departments'),
                axios.get('/api/positions')
            ]);
            setDropdowns({ nationalities: nat.data, departments: dep.data, positions: pos.data });
        } catch {
            showSnackbar('Failed to load dropdowns');
        }
    };

    const showSnackbar = (message) => setSnackbar({ open: true, message });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async () => {
        try {
            const payload = {
                ...formData,
                nationalityId: parseInt(formData.nationalityId) || null,
                departmentId: parseInt(formData.departmentId) || null,
                positionId: parseInt(formData.positionId) || null,
                managerId: formData.managerId ? parseInt(formData.managerId) : null
            };

            await axios.post('/api/employees', payload);
            showSnackbar('✅ Employee added successfully');
            setOpenForm(false);
            setFormData(initialFormData());
            fetchEmployees();
        } catch (error) {
            const msg = error?.response?.data?.error || '❌ Failed to save employee';
            showSnackbar(msg);
            console.error('Submit error:', error?.response?.data || error.message);
        }
    };

    return (
        <Container>
            <Typography variant="h3" gutterBottom>Employee Directory</Typography>
            <Button variant="contained" onClick={() => setOpenForm(true)} sx={{ mb: 3 }}>➕ Add Employee</Button>
            {loading ? <CircularProgress /> : (
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>PIN</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Department</TableCell>
                            <TableCell>Position</TableCell>
                            <TableCell>Status</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {employees.map((emp) => (
                            <TableRow key={emp.id}>
                                <TableCell>{emp.id}</TableCell>
                                <TableCell>{emp.pin}</TableCell>
                                <TableCell>{emp.firstName} {emp.lastName}</TableCell>
                                <TableCell>{emp.department?.name}</TableCell>
                                <TableCell>{emp.position?.name}</TableCell>
                                <TableCell>{emp.employmentStatus}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}

            <Dialog open={openForm} onClose={() => setOpenForm(false)} maxWidth="md" fullWidth>
                <DialogTitle>Add New Employee</DialogTitle>
                <DialogContent>
                    <Grid container spacing={2} sx={{ mt: 1 }}>
                        {[{ label: 'PIN', name: 'pin' }, { label: 'First Name', name: 'firstName' },
                          { label: 'Last Name', name: 'lastName' }, { label: 'Birth Date', name: 'birthDate', type: 'date' },
                          { label: 'Date of Hire', name: 'dateOfHire', type: 'date' }, { label: 'Phone', name: 'phoneNumber' },
                          { label: 'Email', name: 'email' }, { label: 'Address', name: 'address' },
                          { label: 'Emergency Contact Name', name: 'emergencyContactName' },
                          { label: 'Emergency Contact Phone', name: 'emergencyContactPhone' },
                          { label: 'Tax ID', name: 'taxId' }, { label: 'Bank Account Number', name: 'bankAccountNumber' }]
                          .map(({ label, name, type = 'text' }) => (
                            <Grid item xs={6} key={name}>
                                <TextField
                                    label={label} name={name} type={type} fullWidth required
                                    value={formData[name] || ''} onChange={handleChange}
                                    InputLabelProps={type === 'date' ? { shrink: true } : {}}
                                />
                            </Grid>
                        ))}

                        {[{ name: 'gender', label: 'Gender', options: ['Male', 'Female', 'Other'] },
                          { name: 'maritalStatus', label: 'Marital Status', options: ['Single', 'Married', 'Divorced', 'Widowed'] },
                          { name: 'employmentStatus', label: 'Employment Status', options: ['Active', 'On Leave', 'Terminated'] },
                          { name: 'employmentType', label: 'Employment Type', options: ['Full-Time', 'Part-Time', 'Contractor'] }]
                          .map(({ name, label, options }) => (
                            <Grid item xs={6} key={name}>
                                <TextField select label={label} name={name} fullWidth value={formData[name] || ''} onChange={handleChange}>
                                    {options.map(opt => <MenuItem key={opt} value={opt}>{opt}</MenuItem>)}
                                </TextField>
                            </Grid>
                        ))}

                        {[{ name: 'nationalityId', label: 'Nationality', options: dropdowns.nationalities, idKey: 'nationalityId', labelKey: 'name' },
                          { name: 'departmentId', label: 'Department', options: dropdowns.departments, idKey: 'departmentId', labelKey: 'name' },
                          { name: 'positionId', label: 'Position', options: dropdowns.positions, idKey: 'id', labelKey: 'name' }]
                          .map(({ name, label, options, idKey, labelKey }) => (
                            <Grid item xs={6} key={name}>
                                <TextField select label={label} name={name} fullWidth value={formData[name] || ''} onChange={handleChange}>
                                    {options.map(opt => <MenuItem key={opt[idKey]} value={opt[idKey]}>{opt[labelKey]}</MenuItem>)}
                                </TextField>
                            </Grid>
                        ))}
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenForm(false)}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">Save</Button>
                </DialogActions>
            </Dialog>

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
