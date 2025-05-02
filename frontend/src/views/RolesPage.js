import React, { useState, useEffect } from "react";
import { fetchRoles } from "../services/roleService"; // Your service to fetch roles
import { Typography, Paper, Table, TableHead, TableBody, TableCell, TableRow, Box } from "@mui/material";

const RolesPage = () => {
    const [roles, setRoles] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        loadRoles();
    }, []);

    const loadRoles = async () => {
        try {
            const fetchedRoles = await fetchRoles();  // Fetch roles from backend
            setRoles(fetchedRoles);
        } catch (error) {
            setError("Error fetching roles");
        }
    };

    // Hardcoded permissions based on role name
    const rolePermissions = {
        Admin: ["dashboard", "userManagement", "roleManagement", "employeeManagement", "departmentManagement"],
        HR: ["dashboard", "employeeManagement"],
        Employee: ["dashboard"],
    };

    // Map permissions to descriptions
    const getPermissionsDescription = (roleName) => {
        const permissions = rolePermissions[roleName] || [];
        const descriptions = {
            dashboard: "Can access the dashboard.",
            userManagement: "Can manage users.",
            roleManagement: "Can manage roles.",
            employeeManagement: "Can manage employees.",
            departmentManagement: "Can manage departments.",
        };

        return permissions.map(permission => descriptions[permission] || "Unknown permission").join(", ");
    };

    return (
        <Box p={3}>
            <Typography variant="h5" gutterBottom>Roles Management</Typography>
            {error && <Typography color="error">{error}</Typography>}

            <Paper sx={{ mt: 3 }}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Role ID</TableCell>
                            <TableCell>Role Name</TableCell>
                            <TableCell>Permissions</TableCell>
                            <TableCell>Description</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {roles.map((role) => (
                            <TableRow key={role.id}>
                                <TableCell>{role.id}</TableCell>
                                <TableCell>{role.name}</TableCell>
                                <TableCell>{rolePermissions[role.name]?.join(", ") || "No permissions"}</TableCell>
                                <TableCell>{getPermissionsDescription(role.name)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>
        </Box>
    );
};

export default RolesPage;
