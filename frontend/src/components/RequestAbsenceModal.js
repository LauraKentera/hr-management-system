import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Alert,
  CircularProgress,
  Box,
  Typography
} from '@mui/material';
import ApiEndpoints from '../api/ApiEndpoints';

const RequestAbsenceModal = ({ open, onClose, onSuccess, employeeId }) => {
  // Form state
  const [formData, setFormData] = useState({
    employeeId: employeeId || '',
    absenceTypeId: '',
    startDate: '',
    endDate: '',
    notes: ''
  });

  const [absenceTypes, setAbsenceTypes] = useState([]);
  const [loading, setLoading] = useState({
    types: false,
    submit: false
  });
  const [error, setError] = useState('');

  // Fetch absence types when modal opens
  useEffect(() => {
    const fetchAbsenceTypes = async () => {
      setLoading(prev => ({ ...prev, types: true }));
      setError('');
      try {
        const response = await fetch(ApiEndpoints.absenceTypes.getAll, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        if (!response.ok) throw new Error('Failed to fetch absence types');
        const data = await response.json();
        setAbsenceTypes(Array.isArray(data) ? data : []);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(prev => ({ ...prev, types: false }));
      }
    };

    if (open) {
      fetchAbsenceTypes();
      // Reset form when opening
      setFormData({
        employeeId: employeeId || '',
        absenceTypeId: '',
        startDate: '',
        endDate: '',
        notes: ''
      });
    }
  }, [open, employeeId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const validateForm = () => {
    // Clear previous errors
    setError('');

    // Validate employee ID
    if (!formData.employeeId || !/^\d+$/.test(formData.employeeId)) {
      setError('Please enter a valid employee ID');
      return false;
    }

    // Validate absence type
    if (!formData.absenceTypeId) {
      setError('Please select an absence type');
      return false;
    }

    // Validate dates
    if (!formData.startDate || !formData.endDate) {
      setError('Please select both start and end dates');
      return false;
    }

    const startDate = new Date(formData.startDate);
    const endDate = new Date(formData.endDate);

    if (startDate > endDate) {
      setError('End date must be after start date');
      return false;
    }

    return true;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;
  
    setLoading(prev => ({ ...prev, submit: true }));
    setError('');
  
    try {
      const payload = {
        employeeId: Number(formData.employeeId),
        absenceTypeId: Number(formData.absenceTypeId),
        startDate: formData.startDate,
        endDate: formData.endDate,
        notes: formData.notes || null,
        status: 'Pending'
      };
  
      // Simplified headers
      const headers = {
        'Content-Type': 'application/json'
      };
  
      // Only add Authorization if token exists and isn't too large
      const token = localStorage.getItem('token');
      if (token && token.length < 1000) { // Adjust length as needed
        headers['Authorization'] = `Bearer ${token}`;
      }
  
      // Create absence
      const response = await fetch(ApiEndpoints.absences.create, {
        method: 'POST',
        headers,
        body: JSON.stringify(payload)
      });
  
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || errorData.error || 'Submission failed');
      }
  
      const responseData = await response.json();
      const absenceId = responseData.absenceId;
  
      // Create approval request
      try {
        const approvalResponse = await fetch(ApiEndpoints.approvals?.create || '/api/approvals', {
          method: 'POST',
          headers,
          body: JSON.stringify({
            requestType: 'Absence',
            employeeId: Number(formData.employeeId),
            relatedId: absenceId,
            status: 'Pending',
            requestedBy: Number(formData.employeeId),
            timestamp: new Date().toISOString()
          })
        });
  
        if (!approvalResponse.ok) {
          console.warn('Approval request failed, but absence was created');
        }
      } catch (approvalError) {
        console.warn('Approval request error:', approvalError);
      }
  
      if (typeof onSuccess === 'function') onSuccess();
      onClose();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(prev => ({ ...prev, submit: false }));
    }
  };


  const calculateDuration = () => {
    if (!formData.startDate || !formData.endDate) return 0;
    try {
      const start = new Date(formData.startDate);
      const end = new Date(formData.endDate);
      const diffTime = Math.abs(end - start);
      return Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
    } catch {
      return 0;
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Request New Absence</DialogTitle>
      <DialogContent dividers>
        {/* Employee ID Field - disabled if employeeId is provided */}
        <TextField
          label="Employee ID"
          name="employeeId"
          value={formData.employeeId}
          onChange={handleChange}
          fullWidth
          margin="normal"
          disabled={!!employeeId}
          inputProps={{ 
            inputMode: 'numeric',
            pattern: '[0-9]*'
          }}
        />

        {/* Absence Type Dropdown */}
        <FormControl fullWidth margin="normal" required>
          <InputLabel>Absence Type</InputLabel>
          <Select
            name="absenceTypeId"
            value={formData.absenceTypeId}
            label="Absence Type *"
            onChange={handleChange}
            disabled={loading.types}
          >
            <MenuItem value=""><em>Select Type</em></MenuItem>
            {loading.types ? (
              <MenuItem disabled>Loading types...</MenuItem>
            ) : (
              absenceTypes.map(type => (
                <MenuItem key={type.id} value={type.id}>
                  {type.name}
                </MenuItem>
              ))
            )}
          </Select>
        </FormControl>

        {/* Date Fields */}
        <Box display="flex" gap={2} mt={2}>
          <TextField
            label="Start Date *"
            type="date"
            name="startDate"
            value={formData.startDate}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
            fullWidth
            inputProps={{
              min: new Date().toISOString().split('T')[0] // Prevent selecting past dates
            }}
          />
          <TextField
            label="End Date *"
            type="date"
            name="endDate"
            value={formData.endDate}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
            fullWidth
            inputProps={{
              min: formData.startDate || new Date().toISOString().split('T')[0]
            }}
          />
        </Box>

        {/* Calculated duration display */}
        {formData.startDate && formData.endDate && (
          <Typography variant="body2" color="text.secondary" mt={1}>
            Duration: {calculateDuration()} days
          </Typography>
        )}

        {/* Notes Field */}
        <TextField
          label="Notes (Optional)"
          name="notes"
          value={formData.notes}
          onChange={handleChange}
          multiline
          rows={4}
          fullWidth
          margin="normal"
        />

        {/* Error Display */}
        {error && (
          <Alert severity="error" sx={{ mt: 2 }}>
            {error}
          </Alert>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} disabled={loading.submit}>
          Cancel
        </Button>
        <Button
          onClick={handleSubmit}
          variant="contained"
          color="primary"
          disabled={loading.submit || !formData.absenceTypeId || !formData.startDate || !formData.endDate}
        >
          {loading.submit ? (
            <>
              <CircularProgress size={20} sx={{ mr: 1 }} />
              Submitting...
            </>
          ) : 'Submit Request'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default RequestAbsenceModal;