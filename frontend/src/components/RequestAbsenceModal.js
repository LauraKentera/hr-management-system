import React, { useState, useEffect } from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button, FormControl, InputLabel, Select, MenuItem, Alert
} from '@mui/material';
import ApiEndpoints from '../api/ApiEndpoints'; // ✅ ADD THIS

const RequestAbsenceModal = ({ open, onClose, onSuccess }) => {
  const [formData, setFormData] = useState({
    type: '',
    startDate: '',
    endDate: '',
    notes: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (open) {
      setFormData({ type: '', startDate: '', endDate: '', notes: '' });
      setError('');
    }
  }, [open]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async () => {
    if (!formData.type || !formData.startDate || !formData.endDate) {
      setError('Please fill out all required fields.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await fetch(ApiEndpoints.absences.create, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          employeeId: 1, // Simulated user ID
          absenceTypeId: 1, // Hardcoded for now — this should be resolved dynamically
          startDate: formData.startDate,
          endDate: formData.endDate,
          notes: formData.notes
        })
      });

      if (!response.ok) throw new Error(`Server error: ${response.status}`);

      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      setError('Failed to submit absence request.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Request Absence</DialogTitle>
      <DialogContent dividers>
        <FormControl fullWidth margin="normal">
          <InputLabel id="absence-type-label">Absence Type</InputLabel>
          <Select
            labelId="absence-type-label"
            name="type"
            value={formData.type}
            onChange={handleChange}
          >
            <MenuItem value=""><em>Select Type</em></MenuItem>
            <MenuItem value="Annual Leave">Annual Leave</MenuItem>
            <MenuItem value="Sick Leave">Sick Leave</MenuItem>
            <MenuItem value="Personal Leave">Personal Leave</MenuItem>
          </Select>
        </FormControl>

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

        {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} disabled={loading}>Cancel</Button>
        <Button onClick={handleSubmit} variant="contained" disabled={loading}>Submit</Button>
      </DialogActions>
    </Dialog>
  );
};

export default RequestAbsenceModal;
