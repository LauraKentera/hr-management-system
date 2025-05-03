import React, { useState, useEffect } from 'react';
import {
    Box, Typography, Button, Grid,
    Table, TableHead, TableRow, TableCell, TableBody, TableContainer,
    Paper, CircularProgress, Alert, Chip, Tabs, Tab
} from '@mui/material';
import ApiEndpoints from '../api/ApiEndpoints';
import RequestAbsenceModal from '../components/RequestAbsenceModal';
import AbsenceTypeChart from '../components/AbsenceTypeChart';

const AbsenceView = () => {
    const [absences, setAbsences] = useState([]);
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState({ main: true, action: false });
    const [error, setError] = useState('');
    const [openModal, setOpenModal] = useState(false);
    const [selectedTab, setSelectedTab] = useState(0);
    
    // Get user data from localStorage
    const currentUser = {
        id: localStorage.getItem('userId'),
        role: localStorage.getItem('role'),
        employeeId: localStorage.getItem('employeeId')
    };

    const fetchData = async () => {
        try {
            setLoading(prev => ({ ...prev, main: true }));
            setError('');
            
            let endpoint = ApiEndpoints.absences.getAll;
            const statusMap = {
                1: 'Approved',
                2: 'Rejected',
                3: 'Pending'
            };
            
            if (statusMap[selectedTab]) {
                endpoint += `?status=${statusMap[selectedTab]}`;
            }

            const absencesRes = await fetch(endpoint, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });

            if (!absencesRes.ok) {
                throw new Error(`Failed to fetch absences: ${absencesRes.status}`);
            }

            const absencesData = await absencesRes.json();
            setAbsences(absencesData);

            if (employees.length === 0) {
                const employeesRes = await fetch(ApiEndpoints.employee.getAll, {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
                });

                if (!employeesRes.ok) {
                    throw new Error(`Failed to fetch employees: ${employeesRes.status}`);
                }

                const employeesData = await employeesRes.json();
                setEmployees(employeesData);
            }
        } catch (err) {
            console.error('Fetch error:', err);
            setError(err.message);
            setAbsences([]);
        } finally {
            setLoading(prev => ({ ...prev, main: false }));
        }
    };

    useEffect(() => {
        fetchData();
    }, [selectedTab]);

    const handleCreateAbsence = async (absenceData) => {
        setLoading(prev => ({ ...prev, action: true }));
        try {
            const response = await fetch(ApiEndpoints.absences.create, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    employeeId: currentUser.employeeId,
                    absenceTypeId: absenceData.absenceTypeId,
                    startDate: absenceData.startDate,
                    endDate: absenceData.endDate,
                    notes: absenceData.notes,
                    status: 'Pending'
                })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to create absence');
            }

            const newAbsence = await response.json();

            const approvalResponse = await fetch(ApiEndpoints.approvals.create, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    requestType: 'Absence',
                    employeeId: currentUser.employeeId,
                    relatedId: newAbsence.absenceId,
                    status: 'Pending',
                    requestedBy: currentUser.id,
                    timestamp: new Date().toISOString()
                })
            });

            if (!approvalResponse.ok) {
                throw new Error('Failed to create approval request');
            }

            await fetchData();
            return true;
        } catch (err) {
            setError(err.message);
            return false;
        } finally {
            setLoading(prev => ({ ...prev, action: false }));
        }
    };

    const handleApproveReject = async (absenceId, action) => {
        setLoading(prev => ({ ...prev, action: true }));
        setError('');
        
        try {
          // Convert currentUser.id to number if it's a string
          const approverId = Number(currentUser.id);
          
          // Create query parameters
          const queryParams = new URLSearchParams({
            approvedBy: approverId
          });
      
          const response = await fetch(
            `http://localhost:8080/api/employee-absences/${absenceId}/${action}?${queryParams}`,
            {
              method: 'PUT',
              headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json',
              },
              // Some APIs might expect an empty body for PUT requests with query params
              body: JSON.stringify({}) // Or remove body entirely if not needed
            }
          );
      
          if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Failed to ${action} absence`);
          }
      
          // Update UI state
          setAbsences(prev => prev.map(absence => 
            absence.absenceId === absenceId
              ? { 
                  ...absence, 
                  status: action === 'approve' ? 'Approved' : 'Rejected',
                  approvedBy: approverId // Update with approver info if needed
                }
              : absence
          ));
        } catch (err) {
          setError(err.message);
        } finally {
          setLoading(prev => ({ ...prev, action: false }));
        }
      };

    const getEmployeeName = (employeeId) => {
        const employee = employees.find(e => e.id === employeeId);
        return employee ? `${employee.firstName} ${employee.lastName}` : 'Unknown';
    };

    const getStatusChip = (status) => {
        let color;
        switch (status) {
            case 'Approved': color = 'success'; break;
            case 'Rejected': color = 'error'; break;
            default: color = 'warning';
        }
        return <Chip label={status} color={color} size="small" />;
    };

    const handleTabChange = (event, newValue) => {
        setSelectedTab(newValue);
    };

    if (!currentUser.id) {
        return (
            <Box p={3}>
                <CircularProgress />
                <Typography>Loading user data...</Typography>
            </Box>
        );
    }

    const filteredAbsences = absences.filter(absence => {
        switch (selectedTab) {
            case 1: return absence.status === 'Approved';
            case 2: return absence.status === 'Rejected';
            case 3: return absence.status === 'Pending';
            default: return true; // Show all for tab 0
        }
    });

    return (
        <Box p={3}>
            <Typography variant="h4" gutterBottom>Absence Management</Typography>

            {error && (
                <Alert severity="error" sx={{ mb: 2 }} onClose={() => setError('')}>
                    {error}
                </Alert>
            )}

            <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 3 }}>
                <Tabs value={selectedTab} onChange={handleTabChange}>
                    <Tab label="All" />
                    <Tab label="Approved" />
                    <Tab label="Rejected" />
                    {currentUser.role === 'Admin' && <Tab label="Pending" />}
                </Tabs>
            </Box>

                <Grid item xs={12} md={8}>
                    <Paper elevation={3} sx={{ p: 2 }}>
                        <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                            <Typography variant="h6">
                                {selectedTab === 0 && 'All Absences'}
                                {selectedTab === 1 && 'Approved Absences'}
                                {selectedTab === 2 && 'Rejected Absences'}
                                {selectedTab === 3 && 'Pending Approvals'}
                            </Typography>
                            {/* Changed this line to show for all roles */}
                            <Button
                                variant="contained"
                                onClick={() => setOpenModal(true)}
                                disabled={loading.main}
                                sx={{ textTransform: 'none' }}
                            >
                                + New Absence Request
                            </Button>
                        </Box>

                        {loading.main ? (
                            <Box display="flex" justifyContent="center" p={4}>
                                <CircularProgress />
                            </Box>
                        ) : absences.length === 0 ? (
                            <Typography variant="body1" color="text.secondary" textAlign="center" py={4}>
                                No absences found
                            </Typography>
                        ) : (
                            <TableContainer>
                                <Table>
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Employee</TableCell>
                                            <TableCell>Type</TableCell>
                                            <TableCell>Dates</TableCell>
                                            <TableCell>Days</TableCell>
                                            <TableCell>Status</TableCell>
                                            {currentUser.role === 'Admin' && selectedTab === 3 && (
                                                <TableCell>Actions</TableCell>
                                            )}
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {filteredAbsences.map((absence) => (
                                            <TableRow key={absence.absenceId}>
                                                <TableCell>{getEmployeeName(absence.employeeId)}</TableCell>
                                                <TableCell>{absence.absenceType?.name || 'Unknown'}</TableCell>
                                                <TableCell>
                                                    {new Date(absence.startDate).toLocaleDateString()} -{' '}
                                                    {new Date(absence.endDate).toLocaleDateString()}
                                                </TableCell>
                                                <TableCell>
                                                    {Math.ceil(
                                                        (new Date(absence.endDate) - new Date(absence.startDate)) /
                                                        (1000 * 60 * 60 * 24)
                                                    ) + 1}
                                                </TableCell>
                                                <TableCell>{getStatusChip(absence.status)}</TableCell>
                                                {currentUser.role === 'Admin' && absence.status === 'Pending' && (
                                                    <TableCell>
                                                        <Button
                                                            size="small"
                                                            variant="contained"
                                                            color="success"
                                                            onClick={() => handleApproveReject(absence.absenceId, 'approve')}
                                                            sx={{ mr: 1 }}
                                                            disabled={loading.action}
                                                        >
                                                            Approve
                                                        </Button>
                                                        <Button
                                                            size="small"
                                                            variant="outlined"
                                                            color="error"
                                                            onClick={() => handleApproveReject(absence.absenceId, 'reject')}
                                                            disabled={loading.action}
                                                        >
                                                            Reject
                                                        </Button>
                                                    </TableCell>
                                                )}
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        )}
                    </Paper>
                </Grid>

            <RequestAbsenceModal
                open={openModal}
                onClose={() => setOpenModal(false)}
                onSubmit={handleCreateAbsence}
                employeeId={currentUser.employeeId}
                loading={loading.action}
            />
        </Box>
    );
};

export default AbsenceView;