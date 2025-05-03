import React, { useState, useEffect } from 'react';
import {
    Box, Typography, Button, Table, TableHead, TableRow, TableCell, TableBody, TableContainer,
    Paper, CircularProgress, Alert, Collapse, IconButton, Divider
} from '@mui/material';
import { KeyboardArrowDown, KeyboardArrowUp } from '@mui/icons-material'; // For collapsible icon
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
        employmentStatus: 'Active',
        maritalStatus: 'Single',
        employmentType: 'Full-Time',
        taxId: '',
        phoneNumber: '',
        email: '',
        address: '',
        gender: 'Male',
        emergencyContactName: '',
        emergencyContactPhone: '',
        managerId: null,
    });
    const [expandedEmployeeId, setExpandedEmployeeId] = useState(null); // Track expanded row

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
            handleCloseModal();  // Close modal after creating employee
        } catch (err) {
            console.error(err);
            setError('Failed to create employee.');
        }
    };

    const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString();

    // Toggle function for expanding/collapsing employee row
    const handleRowClick = (employeeId) => {
        setExpandedEmployeeId((prevId) => (prevId === employeeId ? null : employeeId)); // Toggle between open and close
    };

    // Function to delete an employee
    const handleDeleteEmployee = async (employeeId) => {
        try {
            const response = await fetch(`${ApiEndpoints.employee.delete(employeeId)}`, {
                method: 'DELETE',
            });
            if (!response.ok) throw new Error(`Status: ${response.status}`);
            setEmployees((prev) => prev.filter((e) => e.id !== employeeId)); // Remove the deleted employee from the list
        } catch (err) {
            console.error('Error deleting employee:', err);
            setError('Failed to delete employee.');
        }
    };

    return (
        <Box p={3} sx={{ minHeight: '100vh' }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h4" sx={{ fontWeight: 600, color: '#004e92' }}>Employee Directory</Typography>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleOpenModal}
                    sx={{
                        backgroundColor: '#0077b6',
                        '&:hover': { backgroundColor: '#005f8a' },
                        textTransform: 'none',
                        boxShadow: 3,
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
                <TableContainer component={Paper} sx={{ boxShadow: 3, borderRadius: 2 }}>
                    <Table>
                        <TableHead sx={{ backgroundColor: '#0077b6' }}>
                            <TableRow>
                                <TableCell sx={{ color: '#fff', fontWeight: 600 }}>ID</TableCell>
                                <TableCell sx={{ color: '#fff', fontWeight: 600 }}>Name</TableCell>
                                <TableCell sx={{ color: '#fff', fontWeight: 600 }}>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {employees.map((emp) => (
                                <React.Fragment key={emp.id}>
                                    <TableRow
                                        onClick={() => handleRowClick(emp.id)}
                                        sx={{
                                            cursor: 'pointer',
                                            '&:hover': { backgroundColor: '#f1f1f1' },
                                        }}
                                    >
                                        <TableCell>{emp.id}</TableCell>
                                        <TableCell>{emp.firstName} {emp.lastName}</TableCell>
                                        <TableCell>
                                            <Box display="flex" alignItems="center">
                                                <IconButton size="small" onClick={() => handleRowClick(emp.id)}>
                                                    {expandedEmployeeId === emp.id ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
                                                </IconButton>
                                                <Button
                                                    variant="outlined"
                                                    color="error"
                                                    size="small"
                                                    onClick={(e) => {
                                                        e.stopPropagation();  // Prevent row click from triggering collapse
                                                        handleDeleteEmployee(emp.id);
                                                    }}
                                                    sx={{
                                                        borderColor: '#d32f2f',
                                                        color: '#d32f2f',
                                                        '&:hover': { borderColor: '#b71c1c', color: '#b71c1c' },
                                                        marginLeft: 1,
                                                    }}
                                                >
                                                    Delete
                                                </Button>
                                            </Box>
                                        </TableCell>
                                    </TableRow>
                                    <TableRow>
                                        <TableCell colSpan={3} sx={{ padding: 0 }}>
                                            <Collapse in={expandedEmployeeId === emp.id} timeout="auto" unmountOnExit>
                                                <Box p={2} sx={{ backgroundColor: '#fafafa', borderRadius: 1 }}>
                                                    <Typography variant="h6" sx={{ fontWeight: 600, marginBottom: 1 }}>Employee Details</Typography>
                                                    <Divider sx={{ marginBottom: 1 }} />
                                                    <Typography><strong>PIN:</strong> {emp.pin}</Typography>
                                                    <Typography><strong>Birth Date:</strong> {formatDate(emp.birthDate)}</Typography>
                                                    <Typography><strong>Hire Date:</strong> {formatDate(emp.dateOfHire)}</Typography>
                                                    <Typography><strong>Dismissal Date:</strong> {emp.dateOfDismissal ? formatDate(emp.dateOfDismissal) : 'N/A'}</Typography>
                                                    <Typography><strong>Phone:</strong> {emp.phoneNumber}</Typography>
                                                    <Typography><strong>Email:</strong> {emp.email}</Typography>
                                                    <Typography><strong>Address:</strong> {emp.address}</Typography>
                                                    <Typography><strong>Gender:</strong> {emp.gender}</Typography>
                                                    <Typography><strong>Nationality:</strong> {emp.nationality_name || 'N/A'}</Typography>
                                                    <Typography><strong>Department:</strong> {emp.department_name || 'N/A'}</Typography>
                                                    <Typography><strong>Position:</strong> {emp.position_name || 'N/A'}</Typography>
                                                    <Typography><strong>Employment Status:</strong> {emp.employmentStatus}</Typography>
                                                    <Typography><strong>Employment Type:</strong> {emp.employmentType}</Typography>
                                                    <Typography><strong>Marital Status:</strong> {emp.maritalStatus}</Typography>
                                                    <Typography><strong>Emergency Contact:</strong> {emp.emergencyContactName} ({emp.emergencyContactPhone})</Typography>
                                                    <Typography><strong>Manager:</strong> {emp.manager_first_name} {emp.manager_last_name || 'N/A'}</Typography>
                                                    <Typography><strong>Tax ID:</strong> {emp.taxId}</Typography>
                                                    <Typography><strong>Bank Account:</strong> {emp.bankAccountNumber}</Typography>
                                                </Box>
                                            </Collapse>
                                        </TableCell>
                                    </TableRow>
                                </React.Fragment>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

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
