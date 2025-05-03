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
    Paper
} from "@mui/material";
import { getRole } from "../utils/auth";
import Header from '../components/Topbar';

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
        } catch (error) {
            console.error("Error saving benefit", error);
    
            // Optional: optimistic refresh if backend error is non-blocking
            if (error.response?.status === 500) {
                console.warn("Server error after creating benefit. Attempting to reload benefits...");
            } else {
                return; // for other errors, stop further execution
            }
        } finally {
            await loadBenefits();
            handleCloseForm();
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
            <Header />
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h5" sx={{ fontWeight: 600, color: '#004e92' }}>Benefits</Typography>
                {isEditable && (
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={() => handleOpenForm()}
                        sx={{
                            backgroundColor: "#0077b6",
                            "&:hover": { backgroundColor: "#005f8a" },
                            textTransform: "none",
                        }}
                    >
                        Add Benefit
                    </Button>
                )}
            </Box>

            {loading ? (
                <Typography>Loading...</Typography>
            ) : (
                <Paper sx={{ p: 2, boxShadow: 2 }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Name</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Description</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Taxable</TableCell>
                                <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Active</TableCell>
                                {isEditable && <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Actions</TableCell>}
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
                </Paper>
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
                    <Button
                        variant="contained"
                        onClick={handleSubmit}
                        sx={{
                            backgroundColor: "#0077b6",
                            "&:hover": { backgroundColor: "#005f8a" },
                        }}
                    >
                        {editing ? "Update" : "Create"}
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default BenefitsListPage;
