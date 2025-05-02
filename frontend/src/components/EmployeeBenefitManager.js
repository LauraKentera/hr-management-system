import React, { useEffect, useState } from "react";
import {
    fetchAllBenefits,
    fetchEmployeeBenefitById,
    createEmployeeBenefit,
    fetchAllEmployeeBenefits,
    deleteEmployeeBenefit,
} from "../services/benefitApi";
import {
    Box, Typography, Button, Dialog, DialogTitle, DialogContent, DialogActions,
    TextField, Select, MenuItem, Table, TableBody, TableCell, TableHead, TableRow
} from "@mui/material";

function EmployeeBenefitManager({ employeeId }) {
    const [benefits, setBenefits] = useState([]);
    const [employeeBenefits, setEmployeeBenefits] = useState([]);
    const [selectedBenefit, setSelectedBenefit] = useState("");
    const [effectiveDate, setEffectiveDate] = useState("");
    const [open, setOpen] = useState(false);

    useEffect(() => {
        fetchAllBenefits().then(setBenefits);
        fetchAllEmployeeBenefits().then(data => {
            const filtered = data.filter(b => b.employeeId === employeeId);
            setEmployeeBenefits(filtered);
        });
    }, [employeeId]);

    const handleAssign = async () => {
        await createEmployeeBenefit({
            employeeId,
            benefitId: selectedBenefit,
            effectiveDate
        });
        setOpen(false);
        setSelectedBenefit("");
        setEffectiveDate("");
        const updated = await fetchAllEmployeeBenefits();
        setEmployeeBenefits(updated.filter(b => b.employeeId === employeeId));
    };

    const handleDelete = async (benefitId) => {
        await deleteEmployeeBenefit({ employeeId, benefitId });
        const updated = await fetchAllEmployeeBenefits();
        setEmployeeBenefits(updated.filter(b => b.employeeId === employeeId));
    };

    return (
        <Box mt={4}>
            <Typography variant="h6">Assigned Benefits</Typography>
            <Button onClick={() => setOpen(true)} variant="contained" sx={{ mt: 2 }}>
                Assign New Benefit
            </Button>

            <Table sx={{ mt: 2 }}>
                <TableHead>
                    <TableRow>
                        <TableCell>Benefit</TableCell>
                        <TableCell>Effective Date</TableCell>
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {employeeBenefits.map((eb) => (
                        <TableRow key={`${eb.employeeId}-${eb.benefitId}`}>
                            <TableCell>{benefits.find(b => b.benefitId === eb.benefitId)?.name}</TableCell>
                            <TableCell>{eb.effectiveDate}</TableCell>
                            <TableCell>
                                <Button color="error" onClick={() => handleDelete(eb.benefitId)}>Remove</Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>

            <Dialog open={open} onClose={() => setOpen(false)}>
                <DialogTitle>Assign Benefit</DialogTitle>
                <DialogContent>
                    <Select
                        fullWidth
                        value={selectedBenefit}
                        onChange={(e) => setSelectedBenefit(e.target.value)}
                        displayEmpty
                        sx={{ mt: 2 }}
                    >
                        <MenuItem disabled value="">Select Benefit</MenuItem>
                        {benefits.map(b => (
                            <MenuItem key={b.benefitId} value={b.benefitId}>{b.name}</MenuItem>
                        ))}
                    </Select>
                    <TextField
                        fullWidth
                        type="date"
                        label="Effective Date"
                        value={effectiveDate}
                        onChange={(e) => setEffectiveDate(e.target.value)}
                        sx={{ mt: 2 }}
                        InputLabelProps={{ shrink: true }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpen(false)}>Cancel</Button>
                    <Button variant="contained" onClick={handleAssign} disabled={!selectedBenefit || !effectiveDate}>
                        Assign
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
}

export default EmployeeBenefitManager;
