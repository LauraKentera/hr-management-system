import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';
import axios from 'axios';

const BASE_URL = "http://localhost:8080"; // Optional: centralize later

const AbsenceApprovalModal = ({ open, onClose, absence, onSuccess, action, employeeId }) => {
    if (!absence || !employeeId) return null;

    const handleAction = async () => {
        try {
            const url = `${BASE_URL}/api/employee-absences/${absence.absenceId}/${action}?approvedBy=${employeeId}`;
            const response = await axios.put(url);

            if (response.status === 200) {
                onSuccess();
            } else {
                alert("Failed to update absence status.");
            }
        } catch (err) {
            console.error("Approval error:", err.response?.data || err.message);
            alert("Something went wrong while updating the absence.");
        }
    };

    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
            <DialogTitle>{action === 'approve' ? 'Approve' : 'Deny'} Absence</DialogTitle>
            <DialogContent>
                <p>
                    Do you want to {action} the absence of employee #{absence.employeeId}?
                </p>
                <p>
                    Start: {absence.startDate} <br />
                    End: {absence.endDate}
                </p>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleAction} variant="contained" color="primary">
                    {action === 'approve' ? 'Approve' : 'Deny'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default AbsenceApprovalModal;
