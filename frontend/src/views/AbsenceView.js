import React, { useState, useEffect } from 'react';
import {
  Box, Typography, Button,
  Table, TableHead, TableRow, TableCell, TableBody, TableContainer,
  Paper, CircularProgress, Alert
} from '@mui/material';
import ApiEndpoints from '../api/ApiEndpoints';
import RequestAbsenceModal from '../components/RequestAbsenceModal';

const AbsenceView = () => {
  const [absences, setAbsences] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [openModal, setOpenModal] = useState(false);

  const fetchAbsences = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(ApiEndpoints.absences.getAll); // ✅ FIXED
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
  }, []);

  const handleOpenModal = () => setOpenModal(true);
  const handleCloseModal = () => setOpenModal(false);

  const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString();

  return (
    <Box p={2}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
        <Typography variant="h5">All Absences</Typography>
        <Button variant="contained" onClick={handleOpenModal}>Request Absence</Button>
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
              </TableRow>
            </TableHead>
            <TableBody>
              {absences.map((absence, index) => (
                <TableRow key={absence.absenceId || index}>
                  <TableCell>{absence.absenceType?.name || absence.type}</TableCell>
                  <TableCell>{formatDate(absence.startDate)}</TableCell>
                  <TableCell>{formatDate(absence.endDate)}</TableCell>
                  <TableCell>{absence.status || 'Pending'}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

      <RequestAbsenceModal
        open={openModal}
        onClose={handleCloseModal}
        onSuccess={() => {
          handleCloseModal();
          fetchAbsences();
        }}
      />
    </Box>
  );
};

export default AbsenceView;
