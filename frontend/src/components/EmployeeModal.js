import React, { useState, useEffect } from 'react';
import { Box, Typography, Button, Modal, TextField, Select, MenuItem, FormControl, InputLabel, Grid } from '@mui/material';
import ApiEndpoints from '../api/ApiEndpoints'; // Import API endpoints

const EmployeeModal = ({ open, onClose, onSubmit, employeeData, onChange }) => {
    const [nationalities, setNationalities] = useState([]);
    const [departments, setDepartments] = useState([]);
    const [positions, setPositions] = useState([]);
    const [loading, setLoading] = useState(true); // Track loading state

    // Fetch dropdown options for nationality, department, and position
    const fetchDropdownOptions = async () => {
        setLoading(true); // Start loading when fetching data
        try {
            const nationalityResponse = await fetch(ApiEndpoints.nationality.getAll); // Ensure correct API endpoint
            const departmentResponse = await fetch(ApiEndpoints.department.getAll);
            const positionResponse = await fetch(ApiEndpoints.position.getAll);

            const nationalityData = await nationalityResponse.json();
            const departmentData = await departmentResponse.json();
            const positionData = await positionResponse.json();

            console.log("Nationalities:", nationalityData);  // Debugging the fetched data
            console.log("Departments:", departmentData);
            console.log("Positions:", positionData);

            if (Array.isArray(nationalityData)) {
                setNationalities(nationalityData);
            }
            if (Array.isArray(departmentData)) {
                setDepartments(departmentData);
            }
            if (Array.isArray(positionData)) {
                setPositions(positionData);
            }
        } catch (err) {
            console.error('Failed to fetch dropdown data:', err);
        } finally {
            setLoading(false); // Set loading to false after data fetch
        }
    };

    useEffect(() => {
        if (open) {
            fetchDropdownOptions(); // Fetch data when modal is opened
        }
    }, [open]);

    return (
        <Modal
            open={open}
            onClose={onClose}
            aria-labelledby="create-employee-modal"
            aria-describedby="create-employee-form"
        >
            <Box
                sx={{
                    width: '900px',
                    backgroundColor: 'white',
                    margin: 'auto',
                    padding: 3,
                    top: '10%',
                    position: 'absolute',
                    left: '50%',
                    transform: 'translateX(-50%)',
                    borderRadius: 2,
                    boxShadow: 24,
                }}
            >
                <Typography variant="h6" sx={{ mb: 2 }}>Create Employee</Typography>
                <form onSubmit={onSubmit}>
                    <Grid container spacing={2}>
                        {/* Basic form fields */}
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="PIN"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                name="pin"
                                value={employeeData.pin}
                                onChange={onChange}
                                required
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="First Name"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                name="firstName"
                                value={employeeData.firstName}
                                onChange={onChange}
                                required
                            />
                        </Grid>
                        {/* Other form fields like Last Name, Email, Phone, etc. */}
                        
                        {/* Nationality Dropdown */}
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth margin="normal" required>
                                <InputLabel>Nationality</InputLabel>
                                <Select
                                    label="Nationality"
                                    name="nationalityId"
                                    value={employeeData.nationalityId || ''}
                                    onChange={onChange}
                                    sx={{
                                        height: '45px',
                                        minWidth: '200px',
                                    }}
                                    MenuProps={{
                                        PaperProps: {
                                            style: {
                                                maxHeight: 200,
                                                width: 250,
                                            },
                                        },
                                    }}
                                >
                                    {loading ? (
                                        <MenuItem disabled>Loading...</MenuItem>
                                    ) : nationalities.length > 0 ? (
                                        nationalities.map((nationality) => (
                                            <MenuItem key={nationality.nationalityId} value={nationality.nationalityId}>
                                                {nationality.name}
                                            </MenuItem>
                                        ))
                                    ) : (
                                        <MenuItem disabled>No options available</MenuItem>
                                    )}
                                </Select>
                            </FormControl>
                        </Grid>

                        {/* Department Dropdown */}
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth margin="normal" required>
                                <InputLabel>Department</InputLabel>
                                <Select
                                    label="Department"
                                    name="departmentId"
                                    value={employeeData.departmentId || ''}
                                    onChange={onChange}
                                    sx={{
                                        height: '45px',
                                        minWidth: '200px',
                                    }}
                                    MenuProps={{
                                        PaperProps: {
                                            style: {
                                                maxHeight: 200,
                                                width: 250,
                                            },
                                        },
                                    }}
                                >
                                    {loading ? (
                                        <MenuItem disabled>Loading...</MenuItem>
                                    ) : departments.length > 0 ? (
                                        departments.map((department) => (
                                            <MenuItem key={department.departmentId} value={department.departmentId}>
                                                {department.name}
                                            </MenuItem>
                                        ))
                                    ) : (
                                        <MenuItem disabled>No options available</MenuItem>
                                    )}
                                </Select>
                            </FormControl>
                        </Grid>

                        {/* Position Dropdown */}
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth margin="normal" required>
                                <InputLabel>Position</InputLabel>
                                <Select
                                    label="Position"
                                    name="positionId"
                                    value={employeeData.positionId || ''}
                                    onChange={onChange}
                                    sx={{
                                        height: '45px',
                                        minWidth: '200px',
                                    }}
                                    MenuProps={{
                                        PaperProps: {
                                            style: {
                                                maxHeight: 200,
                                                width: 250,
                                            },
                                        },
                                    }}
                                >
                                    {loading ? (
                                        <MenuItem disabled>Loading...</MenuItem>
                                    ) : positions.length > 0 ? (
                                        positions.map((position) => (
                                            <MenuItem key={position.positionId} value={position.positionId}>
                                                {position.name}
                                            </MenuItem>
                                        ))
                                    ) : (
                                        <MenuItem disabled>No options available</MenuItem>
                                    )}
                                </Select>
                            </FormControl>
                        </Grid>

                        {/* Employment Status Dropdown */}
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth margin="normal" required>
                                <InputLabel>Employment Status</InputLabel>
                                <Select
                                    label="Employment Status"
                                    name="employmentStatus"
                                    value={employeeData.employmentStatus || ''}
                                    onChange={onChange}
                                    sx={{
                                        height: '45px',
                                        minWidth: '200px',
                                    }}
                                    MenuProps={{
                                        PaperProps: {
                                            style: {
                                                maxHeight: 200,
                                                width: 250,
                                            },
                                        },
                                    }}
                                >
                                    <MenuItem value="Active">Active</MenuItem>
                                    <MenuItem value="On Leave">On Leave</MenuItem>
                                    <MenuItem value="Terminated">Terminated</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                    </Grid>

                    {/* Submit and Close buttons */}
                    <Button variant="contained" color="primary" type="submit" sx={{ mt: 2 }}>
                        Create
                    </Button>
                    <Button variant="outlined" color="secondary" onClick={onClose} sx={{ mt: 2, ml: 2 }}>
                        Close
                    </Button>
                </form>
            </Box>
        </Modal>
    );
};

export default EmployeeModal;
