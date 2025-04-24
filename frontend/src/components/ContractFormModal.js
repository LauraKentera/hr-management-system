import React, { useState, useEffect } from 'react';
import { Button, TextField, Box } from '@mui/material';

const ContractFormModal = ({ show, onClose, onSuccess, initialData }) => {
  // States to hold form data
  const [employeeId, setEmployeeId] = useState('');
  const [positionId, setPositionId] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  // If there's initial data (for editing), populate the form fields
  useEffect(() => {
    if (initialData) {
      setEmployeeId(initialData.employeeId);
      setPositionId(initialData.positionId);
      setStartDate(initialData.startDate);
      setEndDate(initialData.endDate);
    } else {
      setEmployeeId('');
      setPositionId('');
      setStartDate('');
      setEndDate('');
    }
  }, [initialData]);

  if (!show) return null;

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();

    // Simulate contract submission by logging the data (replace this with actual API calls when your backend is ready)
    const contractData = { employeeId, positionId, startDate, endDate };
    console.log('Contract data:', contractData);

    // Call onSuccess() and close the modal after successful submission
    onSuccess();
    onClose();
  };

  return (
      <div className="modal-backdrop">
        <div className="modal">
          <h3>{initialData ? 'Edit' : 'New'} Contract</h3>
          <form onSubmit={handleSubmit}>
            {/* Employee ID Field */}
            <TextField
                label="Employee ID"
                value={employeeId}
                onChange={(e) => setEmployeeId(e.target.value)}
                required
                fullWidth
                margin="normal"
                type="number"
            />

            {/* Position ID Field */}
            <TextField
                label="Position ID"
                value={positionId}
                onChange={(e) => setPositionId(e.target.value)}
                required
                fullWidth
                margin="normal"
                type="number"
            />

            {/* Start Date Field */}
            <TextField
                label="Start Date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                required
                fullWidth
                margin="normal"
                type="date"
                InputLabelProps={{
                  shrink: true,
                }}
            />

            {/* End Date Field */}
            <TextField
                label="End Date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                required
                fullWidth
                margin="normal"
                type="date"
                InputLabelProps={{
                  shrink: true,
                }}
            />

            {/* Cancel and Save Buttons */}
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
              <Button variant="outlined" color="secondary" onClick={onClose} sx={{ mr: 2 }}>
                Cancel
              </Button>
              <Button type="submit" variant="contained" color="primary">
                Save
              </Button>
            </Box>
          </form>
        </div>
      </div>
  );
};

export default ContractFormModal;
