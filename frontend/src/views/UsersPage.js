import React, { useState, useEffect } from "react";
import { fetchUsers, deleteUser, createUser, updateUser } from "../services/userService";
import { useSnackbar } from "notistack"; // Correct import
import { Table, TableHead, TableBody, TableCell, TableRow, Paper, IconButton, Typography, Box, Button } from "@mui/material";
import { Delete, Edit, Add } from "@mui/icons-material";
import UserFormDialog from "../components/UserFormDialog";
import DeleteConfirmationDialog from "../components/DeleteConfirmationDialog";
import { Chip } from "@mui/material";
import Header from '../components/Topbar';

const UsersPage = () => {
    const [users, setUsers] = useState([]);
    const [roles, setRoles] = useState([]);
    const [error, setError] = useState("");
    const [formOpen, setFormOpen] = useState(false);
    const [editUser, setEditUser] = useState(null);
    const { enqueueSnackbar } = useSnackbar();  // Correct hook import

    const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
    const [userToDelete, setUserToDelete] = useState(null);

    useEffect(() => {
        loadUsers();
        loadRoles();
    }, []);

    const loadUsers = () => {
        fetchUsers()
            .then(setUsers)
            .catch(() => setError("Error loading users"));
    };

    const loadRoles = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/roles"); // adjust if needed
            const data = await res.json();
            setRoles(data);
        } catch {
            setError("Error loading roles");
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteUser(id);
            setUsers(users.filter(user => user.id !== id));
            enqueueSnackbar("User deleted successfully", { variant: "success" });  // Success Snackbar
        } catch {
            enqueueSnackbar("Error deleting user", { variant: "error" });  // Error Snackbar
        }
    };

    const handleSave = async (userData) => {
        try {
            if (editUser) {
                const updated = await updateUser(editUser.id, userData);
                setUsers(users.map(u => (u.id === updated.id ? updated : u)));
                enqueueSnackbar("User updated successfully", { variant: "success" });
            } else {
                const created = await createUser(userData);
                setUsers([...users, created]);
                enqueueSnackbar("User created successfully", { variant: "success" });
            }
            setFormOpen(false);
            setEditUser(null);
        } catch {
            enqueueSnackbar("Error saving user", { variant: "error" });
        }
    };

    const handleDeleteClick = (user) => {
        setUserToDelete(user);
        setOpenDeleteDialog(true);
    };

    const handleConfirmDelete = async () => {
        if (userToDelete) {
            await handleDelete(userToDelete.id);
        }
        setOpenDeleteDialog(false);
        setUserToDelete(null);
    };

    const openAdd = () => {
        setEditUser(null);
        setFormOpen(true);
    };

    const openEdit = (user) => {
        setEditUser(user);
        setFormOpen(true);
    };

    return (
        <Box p={3}>
            <Header /> {/* Add the Header (Topbar) component */}
            <Typography variant="h5" gutterBottom sx={{ fontWeight: 600, color: '#004e92' }}>Users</Typography>
            {error && <Typography color="error">{error}</Typography>}

            <Button
                variant="contained"
                startIcon={<Add />}
                onClick={openAdd}
                sx={{
                    mb: 2,
                    backgroundColor: '#0077b6',
                    '&:hover': { backgroundColor: '#005f8a' },
                    textTransform: 'none',
                }}
            >
                Add User
            </Button>

            <Paper sx={{ boxShadow: 2, p: 2 }}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>ID</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Username</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Role</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map(user => (
                            <TableRow key={user.id}>
                                <TableCell>{user.id}</TableCell>
                                <TableCell>{user.username}</TableCell>
                                <TableCell>
                                    <Chip
                                        label={user.role.name}
                                        color={user.role.name === "Admin" ? "error" : user.role.name === "HR" ? "primary" : "default"}
                                    />
                                </TableCell>
                                <TableCell>
                                    <IconButton onClick={() => openEdit(user)}>
                                        <Edit />
                                    </IconButton>
                                    <IconButton onClick={() => handleDeleteClick(user)}>
                                        <Delete />
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>

            <DeleteConfirmationDialog
                open={openDeleteDialog}
                onClose={() => setOpenDeleteDialog(false)}
                onConfirm={handleConfirmDelete}
            />

            <UserFormDialog
                open={formOpen}
                onClose={() => setFormOpen(false)}
                onSave={handleSave}
                initialData={editUser}
                roles={roles}
            />
        </Box>
    );
};

export default UsersPage;
