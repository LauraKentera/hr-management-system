import React, { useState } from 'react';
import {
    Box, Drawer, List, ListItem, ListItemIcon, ListItemText, Divider
} from '@mui/material';
import {
    People, Work, AccountBox, ExitToApp, ChevronLeft, ChevronRight,
    AttachMoney, CalendarToday, InsertChart, Dashboard, AdminPanelSettings
} from '@mui/icons-material';
import { Link } from 'react-router-dom';
import { getRole } from '../utils/auth';

const Sidebar = () => {
    const [isOpen, setIsOpen] = useState(true);
    const role = getRole(); // "Admin", "HR", "Employee"

    const toggleSidebar = () => setIsOpen(!isOpen);

    return (
        <Drawer
            sx={{
                width: isOpen ? 240 : 60,
                flexShrink: 0,
                '& .MuiDrawer-paper': {
                    width: isOpen ? 240 : 60,
                    boxSizing: 'border-box',
                    transition: 'width 0.3s',
                    position: 'fixed',
                    top: '90px',
                    height: 'calc(100% - 64px)',
                },
            }}
            variant="permanent"
            anchor="left"
        >
            <Box sx={{ width: '100%', height: '100%' }}>
                <List>

                    {/* Sidebar Toggle */}
                    <ListItem button onClick={toggleSidebar}>
                        <ListItemIcon>
                            {isOpen ? <ChevronLeft /> : <ChevronRight />}
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Collapse' : ''} />
                    </ListItem>



                    {/* Common: Absences */}
                    <ListItem button component={Link} to="/absences">
                        <ListItemIcon><CalendarToday /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Absences' : ''} />
                    </ListItem>

                    <Divider />

                    {/* General Access: Benefits */}
                    <ListItem button component={Link} to="/benefits">
                        <ListItemIcon><AttachMoney /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Benefits' : ''} />
                    </ListItem>

                    <Divider />

                    {/* General Access: Contracts */}
                    <ListItem button component={Link} to="/contracts">
                        <ListItemIcon><InsertChart /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Contracts' : ''} />
                    </ListItem>

                    <Divider />

                    {/* General Access: Employees */}
                    <ListItem button component={Link} to="/employees">
                        <ListItemIcon><People /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Employees' : ''} />
                    </ListItem>

                    <Divider />

                    {/* HR + Admin: Departments */}
                    {(role === 'HR' || role === 'Admin') && (
                        <ListItem button component={Link} to="/departments">
                            <ListItemIcon><Work /></ListItemIcon>
                            <ListItemText primary={isOpen ? 'Departments' : ''} />
                        </ListItem>
                    )}

                    {/* HR + Admin: Salaries/Payroll */}
                    {(role === 'HR' || role === 'Admin') && (
                        <ListItem button component={Link} to="/payrolls">
                            <ListItemIcon><AttachMoney /></ListItemIcon>
                            <ListItemText primary={isOpen ? 'Salaries' : ''} />
                        </ListItem>
                    )}

                    {/* Admin Only: Users, Roles, Dashboard */}
                    {role === 'Admin' && (
                        <>
                            <Divider />
                            <ListItem button component={Link} to="/dashboard">
                                <ListItemIcon><Dashboard /></ListItemIcon>
                                <ListItemText primary={isOpen ? 'Dashboard' : ''} />
                            </ListItem>
                            <ListItem button component={Link} to="/users">
                                <ListItemIcon><AccountBox /></ListItemIcon>
                                <ListItemText primary={isOpen ? 'Users' : ''} />
                            </ListItem>
                            <ListItem button component={Link} to="/roles">
                                <ListItemIcon><AdminPanelSettings /></ListItemIcon>
                                <ListItemText primary={isOpen ? 'Roles' : ''} />
                            </ListItem>
                        </>
                    )}

                    <Divider />

                    {/* Logout Section */}
                    <ListItem button component={Link} to="/settings">
                        <ListItemIcon>
                            <AttachMoney />
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Settings' : ''} />
                    </ListItem>

                    <Divider />

                    {/* Logout */}
                    <ListItem button onClick={() => {
                        localStorage.clear();
                        window.location.href = "/login";
                    }}>
                        <ListItemIcon><ExitToApp /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Logout' : ''} />
                    </ListItem>
                </List>
            </Box>
        </Drawer>
    );
};

export default Sidebar;