import React, { useState, useEffect } from "react";
import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    Button, TextField, MenuItem
} from "@mui/material";

const UserFormDialog = ({ open, onClose, onSave, initialData, roles }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [roleId, setRoleId] = useState("");

    useEffect(() => {
        if (initialData) {
            setUsername(initialData.username || "");
            setPassword(""); // Never prefill passwords
            setRoleId(initialData.role?.id || "");
        } else {
            setUsername("");
            setPassword("");
            setRoleId("");
        }
    }, [initialData]);

    const handleSubmit = () => {
        if (!username || !password || !roleId) return;
        onSave({
            username,
            password,
            roleId: parseInt(roleId),
        });
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>{initialData ? "Edit User" : "Add User"}</DialogTitle>
            <DialogContent>
                <TextField
                    fullWidth margin="dense" label="Username"
                    value={username} onChange={(e) => setUsername(e.target.value)}
                />
                <TextField
                    fullWidth margin="dense" label="Password" type="password"
                    value={password} onChange={(e) => setPassword(e.target.value)}
                />
                <TextField
                    select fullWidth margin="dense" label="Role"
                    value={roleId} onChange={(e) => setRoleId(e.target.value)}
                >
                    {roles.map((role) => (
                        <MenuItem key={role.id} value={role.id}>{role.name}</MenuItem>
                    ))}
                </TextField>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button variant="contained" onClick={handleSubmit}>
                    {initialData ? "Update" : "Create"}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default UserFormDialog;
