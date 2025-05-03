import React, { useState, useEffect } from 'react';
import {
    Box, Typography, Button, Table, TableHead, TableRow, TableCell, TableBody, TableContainer,
    Paper, CircularProgress, Alert
} from '@mui/material';
import ApiEndpoints from '../api/ApiEndpoints'; // Import API endpoints
import EmployeeModal from '../components/EmployeeModal'; // Import the modal component

const EmployeeView = () => {
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [openModal, setOpenModal] = useState(false);
    const [newEmployee, setNewEmployee] = useState({
        pin: '',
        firstName: '',
        lastName: '',
        birthDate: '',
        dateOfHire: '',
        nationalityId: '',
        departmentId: '',
        positionId: '',
        employmentStatus: 'Active',  // Default value
        maritalStatus: 'Single',     // Default value
        employmentType: 'Full-Time', // Default value
        taxId: '',
        phoneNumber: '',
        email: '',
        address: '',
        gender: 'Male', // Default value
        emergencyContactName: '',
        emergencyContactPhone: '',
        managerId: null,  // Optional
    });

    const fetchEmployees = async () => {
        setLoading(true);
        setError('');
        try {
            const response = await fetch(ApiEndpoints.employee.getAll);
            if (!response.ok) throw new Error(`Status: ${response.status}`);
            const data = await response.json();
            setEmployees(data);
        } catch (err) {
            console.error('Error fetching employees:', err);
            setError('Failed to fetch employees.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEmployees();
    }, []);

    const handleOpenModal = () => setOpenModal(true);
    const handleCloseModal = () => setOpenModal(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewEmployee((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleCreateEmployee = async (e) => {
        e.preventDefault();

        if (!newEmployee.pin || !newEmployee.lastName || !newEmployee.firstName || !newEmployee.birthDate || !newEmployee.dateOfHire || !newEmployee.nationalityId || !newEmployee.departmentId || !newEmployee.positionId || !newEmployee.taxId) {
            setError("Please fill all required fields.");
            return;
        }

        try {
            const response = await fetch(ApiEndpoints.employee.create, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newEmployee),
            });

            if (!response.ok) throw new Error(`Status: ${response.status}`);
            const createdEmployee = await response.json();
            setEmployees((prevEmployees) => [...prevEmployees, createdEmployee]);
            handleCloseModal(); // Close modal after creating employee
        } catch (err) {
            console.error(err);
            setError('Failed to create employee.');
        }
    };

    const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString();

    return (
        <Box p={3}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h4" sx={{ fontWeight: 600, color: '#004e92' }}>All Employees</Typography>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleOpenModal}
                    sx={{
                        backgroundColor: '#0077b6',
                        '&:hover': { backgroundColor: '#005f8a' },
                        textTransform: 'none',
                    }}
                >
                    Add Employee
                </Button>
            </Box>

            {loading ? (
                <Box display="flex" justifyContent="center" p={4}>
                    <CircularProgress />
                </Box>
            ) : error ? (
                <Alert severity="error">{error}</Alert>
            ) : employees.length === 0 ? (
                <Typography>No employees found.</Typography>
            ) : (
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>ID</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Name</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Email</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Phone</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {employees.map((employee) => (
                                <TableRow key={employee.id}>
                                    <TableCell>{employee.id}</TableCell>
                                    <TableCell>{employee.firstName} {employee.lastName}</TableCell>
                                    <TableCell>{employee.email}</TableCell>
                                    <TableCell>{employee.phoneNumber}</TableCell>
                                    <TableCell>
                                        <Button
                                            variant="outlined"
                                            color="error"
                                            size="small"
                                            onClick={async () => {
                                                await fetch(`${ApiEndpoints.employee.delete(employee.id)}`, {
                                                    method: 'DELETE',
                                                });
                                                setEmployees((prev) => prev.filter((e) => e.id !== employee.id));
                                            }}
                                            sx={{
                                                borderColor: '#d32f2f',
                                                color: '#d32f2f',
                                                '&:hover': { borderColor: '#b71c1c', color: '#b71c1c' },
                                            }}
                                        >
                                            Delete
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

            {/* Pass the necessary props to EmployeeModal */}
            <EmployeeModal
                open={openModal}
                onClose={handleCloseModal}
                onSubmit={handleCreateEmployee}
                employeeData={newEmployee}
                onChange={handleInputChange}
            />
        </Box>
    );
};

export default EmployeeView;
