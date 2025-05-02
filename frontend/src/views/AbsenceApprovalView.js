import React, { useState, useEffect } from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button, FormControl, InputLabel, Select, MenuItem, Alert
} from '@mui/material';

const RequestAbsenceModal = ({ open, onClose, onSuccess }) => {
  // Form state for absence fields
  const [formData, setFormData] = useState({
    type: '',
    startDate: '',
    endDate: '',
    notes: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Reset form when the modal is opened or closed
  useEffect(() => {
    if (open) {
      setFormData({ type: '', startDate: '', endDate: '', notes: '' });
      setError('');
    }
  }, [open]);

  // Handle form field changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Submit the new absence request
  const handleSubmit = async () => {
    // Basic validation: ensure required fields are filled
    if (!formData.type || !formData.startDate || !formData.endDate) {
      setError('Please fill out the absence type, start date, and end date.');
      return;
    }
    setLoading(true);
    setError('');
    try {
      const response = await fetch('/api/employee-absences', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...formData,
          employeeId: 1  // simulated logged-in user ID
        })
      });
      if (!response.ok) {
        throw new Error(`Server error: ${response.status}`);
      }
      // On success, trigger parent callback to refresh data
      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      setError('Failed to submit absence request. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Request Absence</DialogTitle>
      <DialogContent dividers>
        {/* Absence Type Dropdown */}
        <FormControl fullWidth margin="normal">
          <InputLabel id="absence-type-label">Absence Type</InputLabel>
          <Select
            labelId="absence-type-label"
            name="type"
            value={formData.type}
            label="Absence Type"
            onChange={handleChange}
          >
            <MenuItem value=""><em>Select Type</em></MenuItem>
            <MenuItem value="Annual Leave">Annual Leave</MenuItem>
            <MenuItem value="Sick Leave">Sick Leave</MenuItem>
            <MenuItem value="Personal Leave">Personal Leave</MenuItem>
          </Select>
        </FormControl>

        {/* Start Date Picker */}
        <TextField
          label="Start Date"
          type="date"
          name="startDate"
          value={formData.startDate}
          onChange={handleChange}
          InputLabelProps={{ shrink: true }}
          fullWidth
          margin="normal"
        />

        {/* End Date Picker */}
        <TextField
          label="End Date"
          type="date"
          name="endDate"
          value={formData.endDate}
          onChange={handleChange}
          InputLabelProps={{ shrink: true }}
          fullWidth
          margin="normal"
        />

        {/* Notes Field */}
        <TextField
          label="Notes"
          name="notes"
          value={formData.notes}
          onChange={handleChange}
          multiline
          rows={3}
          fullWidth
          margin="normal"
        />

        {/* Error Message (if any) */}
        {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} disabled={loading}>
          Cancel
        </Button>
        <Button 
          onClick={handleSubmit} 
          variant="contained" 
          color="primary" 
          disabled={loading}
        >
          Submit
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default RequestAbsenceModal;
