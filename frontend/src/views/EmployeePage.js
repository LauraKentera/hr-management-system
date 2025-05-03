import React, { useState, useEffect } from 'react';
import {
    Box, Typography, Button,
    Table, TableHead, TableRow, TableCell, TableBody, TableContainer,
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

    // Fetch all employees when the page loads
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

    // Handle modal open/close
    const handleOpenModal = () => setOpenModal(true);
    const handleCloseModal = () => setOpenModal(false);

    // Handle input change in modal
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewEmployee((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    // Handle creating a new employee
    const handleCreateEmployee = async (e) => {
        e.preventDefault();

        // Check if all required fields are provided
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

    // Format date for display
    const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString();

    return (
        <Box p={2}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h5">All Employees</Typography>
                <Button variant="contained" onClick={handleOpenModal}>
                    Add Employee
                </Button>
            </Box>

            {loading ? (
                <CircularProgress />
            ) : error ? (
                <Alert severity="error">{error}</Alert>
            ) : employees.length === 0 ? (
                <Typography>No employees found.</Typography>
            ) : (
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><strong>ID</strong></TableCell>
                                <TableCell><strong>Name</strong></TableCell>
                                <TableCell><strong>Email</strong></TableCell>
                                <TableCell><strong>Phone</strong></TableCell>
                                <TableCell><strong>Actions</strong></TableCell>
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
                                            onClick={async () => {
                                                await fetch(`${ApiEndpoints.employee.delete(employee.id)}`, {
                                                    method: 'DELETE',
                                                });
                                                setEmployees((prev) => prev.filter((e) => e.id !== employee.id));
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
