import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField } from '@mui/material';

const CreateAbsenceTypeModal = ({ open, onClose, onSuccess }) => {
    const [type, setType] = useState('');

    const handleCreate = async () => {
        try {
            const response = await fetch('/api/absence-types', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name: type }),
            });

            if (response.ok) {
                onSuccess();
            } else {
                alert('Error creating absence type');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
            <DialogTitle>Create Absence Type</DialogTitle>
            <DialogContent>
                <TextField
                    label="Absence Type Name"
                    fullWidth
                    value={type}
                    onChange={(e) => setType(e.target.value)}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleCreate} variant="contained" color="primary">
                    Create
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateAbsenceTypeModal;
