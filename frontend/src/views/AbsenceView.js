import React, { useState, useEffect } from 'react';
import {
    Box, Typography, Button,
    Table, TableHead, TableRow, TableCell, TableBody, TableContainer,
    Paper, CircularProgress, Alert
} from '@mui/material';

import ApiEndpoints from '../api/ApiEndpoints';
import RequestAbsenceModal from '../components/RequestAbsenceModal';
import AbsenceApprovalModal from '../components/AbsenceApprovalModal';
import CreateAbsenceTypeModal from '../components/CreateAbsenceTypeModal';

const AbsenceView = () => {
    const [absences, setAbsences] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [openModal, setOpenModal] = useState(false);
    const [openApprovalModal, setOpenApprovalModal] = useState(false);
    const [openCreateAbsenceTypeModal, setOpenCreateAbsenceTypeModal] = useState(false);
    const [absenceToApprove, setAbsenceToApprove] = useState(null);
    const [action, setAction] = useState(null);
    const [userRole, setUserRole] = useState(null);
    const [employeeId, setEmployeeId] = useState(null);

    const fetchAbsences = async () => {
        setLoading(true);
        setError('');
        try {
            const response = await fetch(ApiEndpoints.absences.getAll);
            if (!response.ok) throw new Error(`Status: ${response.status}`);
            const data = await response.json();
            setAbsences(data);
        } catch (err) {
            console.error(err);
            setError('Failed to fetch absences.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchAbsences();
        const role = localStorage.getItem('role');
        const id = Number(localStorage.getItem('employeeId')); // ✅ Convert to number
        setUserRole(role);
        setEmployeeId(id);
    }, []);

    const handleOpenModal = () => setOpenModal(true);
    const handleCloseModal = () => setOpenModal(false);

    const handleOpenApprovalModal = (absence, actionType) => {
        setAbsenceToApprove(absence);
        setAction(actionType);
        setOpenApprovalModal(true);
    };

    const handleCloseApprovalModal = () => {
        setOpenApprovalModal(false);
        setAbsenceToApprove(null);
        setAction(null);
    };

    const handleOpenCreateAbsenceTypeModal = () => setOpenCreateAbsenceTypeModal(true);
    const handleCloseCreateAbsenceTypeModal = () => setOpenCreateAbsenceTypeModal(false);

    const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString();

    return (
        <Box p={2}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h5">All Absences</Typography>

                {userRole === 'Employee' && (
                    <Button variant="contained" onClick={handleOpenModal}>
                        Request Absence
                    </Button>
                )}

                {userRole === 'Admin' && (
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={handleOpenCreateAbsenceTypeModal}
                    >
                        Create Absence Type
                    </Button>
                )}
            </Box>

            {loading ? (
                <CircularProgress />
            ) : error ? (
                <Alert severity="error">{error}</Alert>
            ) : absences.length === 0 ? (
                <Typography>No absences found.</Typography>
            ) : (
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><strong>Type</strong></TableCell>
                                <TableCell><strong>Start Date</strong></TableCell>
                                <TableCell><strong>End Date</strong></TableCell>
                                <TableCell><strong>Status</strong></TableCell>
                                <TableCell><strong>Actions</strong></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {absences.map((absence, index) => (
                                <TableRow key={absence.absenceId || index}>
                                    <TableCell>{absence.absenceType?.name || absence.type}</TableCell>
                                    <TableCell>{formatDate(absence.startDate)}</TableCell>
                                    <TableCell>{formatDate(absence.endDate)}</TableCell>
                                    <TableCell>{absence.status || 'Pending'}</TableCell>
                                    <TableCell>
                                        {(absence.status === 'Pending') &&
                                            (userRole === 'Admin' || userRole === 'HR') && (
                                                <>
                                                    <Button
                                                        variant="outlined"
                                                        onClick={() => handleOpenApprovalModal(absence, 'approve')}
                                                        sx={{ mr: 1 }}
                                                    >
                                                        Approve
                                                    </Button>
                                                    <Button
                                                        variant="outlined"
                                                        color="error"
                                                        onClick={() => handleOpenApprovalModal(absence, 'deny')}
                                                    >
                                                        Deny
                                                    </Button>
                                                </>
                                            )}
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

            {/* Request Absence Modal */}
            <RequestAbsenceModal
                open={openModal}
                onClose={handleCloseModal}
                onSuccess={() => {
                    handleCloseModal();
                    fetchAbsences();
                }}
                employeeId={employeeId}
            />

            {/* Approval Modal */}
            <AbsenceApprovalModal
                open={openApprovalModal}
                onClose={handleCloseApprovalModal}
                absence={absenceToApprove}
                action={action}
                employeeId={employeeId}
                onSuccess={() => {
                    handleCloseApprovalModal();
                    fetchAbsences();
                }}
            />

            {/* Admin-only Create Absence Type */}
            <CreateAbsenceTypeModal
                open={openCreateAbsenceTypeModal}
                onClose={handleCloseCreateAbsenceTypeModal}
                onSuccess={() => {
                    handleCloseCreateAbsenceTypeModal();
                }}
            />
        </Box>
    );
};

export default AbsenceView;
