import React, { useEffect, useState } from "react";
import {
    fetchAllBenefits,
    createBenefit,
    updateBenefit,
    deleteBenefit,
} from "../services/benefitApi";
import {
    Box,
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Checkbox,
    FormControlLabel,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    Typography,
} from "@mui/material";
import { getRole } from "../utils/auth";

// ✅ Pull role and userId directly from localStorage
const role = getRole();
const userId = parseInt(localStorage.getItem("userId"));

const emptyForm = {
    name: "",
    description: "",
    taxable: false,
    active: true,
};

const BenefitsListPage = () => {
    const [benefits, setBenefits] = useState([]);
    const [loading, setLoading] = useState(true);
    const [formOpen, setFormOpen] = useState(false);
    const [editing, setEditing] = useState(false);
    const [formData, setFormData] = useState(emptyForm);
    const [selectedId, setSelectedId] = useState(null);

    const isEditable = role === "Admin" || role === "HR";

    const loadBenefits = async () => {
        try {
            const data = await fetchAllBenefits();
            setBenefits(data);
        } catch (error) {
            console.error("Error fetching benefits", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadBenefits();
    }, []);

    const handleOpenForm = (benefit = null) => {
        if (benefit) {
            setEditing(true);
            setSelectedId(benefit.benefitId);
            setFormData({
                name: benefit.name,
                description: benefit.description,
                taxable: benefit.taxable,
                active: benefit.active,
            });
        } else {
            setEditing(false);
            setSelectedId(null);
            setFormData(emptyForm);
        }
        setFormOpen(true);
    };

    const handleCloseForm = () => {
        setFormOpen(false);
        setFormData(emptyForm);
    };

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSubmit = async () => {
        try {
            if (editing) {
                await updateBenefit(selectedId, formData, userId);
            } else {
                await createBenefit(formData, userId);
            }
            await loadBenefits();
            handleCloseForm();
        } catch (error) {
            console.error("Error saving benefit", error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this benefit?")) {
            try {
                await deleteBenefit(id, userId);
                loadBenefits();
            } catch (error) {
                console.error("Error deleting benefit", error);
            }
        }
    };

    return (
        <Box p={3}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h5">Benefits</Typography>
                {isEditable && (
                    <Button variant="contained" color="primary" onClick={() => handleOpenForm()}>
                        Add Benefit
                    </Button>
                )}
            </Box>

            {loading ? (
                <Typography>Loading...</Typography>
            ) : (
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell>Description</TableCell>
                            <TableCell>Taxable</TableCell>
                            <TableCell>Active</TableCell>
                            {isEditable && <TableCell>Actions</TableCell>}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {benefits.map((benefit) => (
                            <TableRow key={benefit.benefitId}>
                                <TableCell>{benefit.name}</TableCell>
                                <TableCell>{benefit.description}</TableCell>
                                <TableCell>{benefit.taxable ? "Yes" : "No"}</TableCell>
                                <TableCell>{benefit.active ? "Yes" : "No"}</TableCell>
                                {isEditable && (
                                    <TableCell>
                                        <Button
                                            variant="outlined"
                                            size="small"
                                            onClick={() => handleOpenForm(benefit)}
                                            sx={{ mr: 1 }}
                                        >
                                            Edit
                                        </Button>
                                        <Button
                                            variant="outlined"
                                            color="error"
                                            size="small"
                                            onClick={() => handleDelete(benefit.benefitId)}
                                        >
                                            Delete
                                        </Button>
                                    </TableCell>
                                )}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            )}

            <Dialog open={formOpen} onClose={handleCloseForm} fullWidth>
                <DialogTitle>{editing ? "Edit Benefit" : "Add Benefit"}</DialogTitle>
                <DialogContent dividers>
                    <TextField
                        fullWidth
                        margin="normal"
                        label="Name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                    <TextField
                        fullWidth
                        margin="normal"
                        label="Description"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        multiline
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                name="taxable"
                                checked={formData.taxable}
                                onChange={handleChange}
                            />
                        }
                        label="Taxable"
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                name="active"
                                checked={formData.active}
                                onChange={handleChange}
                            />
                        }
                        label="Active"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseForm}>Cancel</Button>
                    <Button variant="contained" onClick={handleSubmit}>
                        {editing ? "Update" : "Create"}
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default BenefitsListPage;
