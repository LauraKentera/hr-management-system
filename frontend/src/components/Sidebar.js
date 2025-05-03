import React, { useState } from 'react';
import {
    Box, Drawer, List, ListItem, ListItemIcon, ListItemText, Divider
} from '@mui/material';
import {
    People, Work, AccountBox, ExitToApp, ChevronLeft, ChevronRight,
    AttachMoney, CalendarToday, InsertChart, Dashboard, AdminPanelSettings
} from '@mui/icons-material';
import { Link, useLocation } from 'react-router-dom';
import { getRole } from '../utils/auth';

const Sidebar = () => {
    const [isOpen, setIsOpen] = useState(true);
    const role = getRole(); // "Admin", "HR", "Employee"
    const location = useLocation();  // Get the current path to highlight active section

    const toggleSidebar = () => setIsOpen(!isOpen);

    return (
        <Drawer
            sx={{
                width: isOpen ? 240 : 60,
                flexShrink: 0,
                '& .MuiDrawer-paper': {
                    width: isOpen ? 240 : 60,
                    boxSizing: 'border-box',
                    transition: 'width 0.3s ease-in-out',
                    position: 'fixed',
                    top: '64px',
                    height: 'calc(100% - 64px)',
                    backgroundColor: '#004e92',  // Blue background for clarity
                    color: '#fff',
                    boxShadow: '4px 0 10px rgba(0, 0, 0, 0.2)', // Subtle depth
                },
            }}
            variant="permanent"
            anchor="left"
        >
            <Box sx={{ width: '100%', height: '100%' }}>
                <List sx={{ paddingTop: 1 }}>
                    {/* Sidebar Toggle */}
                    <ListItem button onClick={toggleSidebar}>
                        <ListItemIcon sx={{ color: '#fff' }}>
                            {isOpen ? <ChevronLeft /> : <ChevronRight />}
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Collapse' : ''} sx={{ color: '#fff' }} />
                    </ListItem>

                    {/* Common: Absences */}
                    <ListItem 
                        button 
                        component={Link} 
                        to="/absences" 
                        sx={{
                            backgroundColor: location.pathname === '/absences' ? '#0077b6' : 'transparent',
                            '&:hover': { backgroundColor: '#005f8a' },
                        }}
                    >
                        <ListItemIcon sx={{ color: '#fff' }}><CalendarToday /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Absences' : ''} sx={{ color: '#fff' }} />
                    </ListItem>

                    <Divider sx={{ backgroundColor: '#fff', opacity: 0.3 }} />

                    {/* General Access: Benefits */}
                    <ListItem 
                        button 
                        component={Link} 
                        to="/benefits"
                        sx={{
                            backgroundColor: location.pathname === '/benefits' ? '#0077b6' : 'transparent',
                            '&:hover': { backgroundColor: '#005f8a' },
                        }}
                    >
                        <ListItemIcon sx={{ color: '#fff' }}><AttachMoney /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Benefits' : ''} sx={{ color: '#fff' }} />
                    </ListItem>

                    <Divider sx={{ backgroundColor: '#fff', opacity: 0.3 }} />

                    {/* General Access: Contracts */}
                    <ListItem 
                        button 
                        component={Link} 
                        to="/contracts"
                        sx={{
                            backgroundColor: location.pathname === '/contracts' ? '#0077b6' : 'transparent',
                            '&:hover': { backgroundColor: '#005f8a' },
                        }}
                    >
                        <ListItemIcon sx={{ color: '#fff' }}><InsertChart /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Contracts' : ''} sx={{ color: '#fff' }} />
                    </ListItem>

                    <Divider sx={{ backgroundColor: '#fff', opacity: 0.3 }} />

                    {/* General Access: Employees */}
                    <ListItem 
                        button 
                        component={Link} 
                        to="/employees"
                        sx={{
                            backgroundColor: location.pathname === '/employees' ? '#0077b6' : 'transparent',
                            '&:hover': { backgroundColor: '#005f8a' },
                        }}
                    >
                        <ListItemIcon sx={{ color: '#fff' }}><People /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Employees' : ''} sx={{ color: '#fff' }} />
                    </ListItem>

                    <Divider sx={{ backgroundColor: '#fff', opacity: 0.3 }} />

                    {/* HR + Admin: Departments */}
                    {(role === 'HR' || role === 'Admin') && (
                        <ListItem 
                            button 
                            component={Link} 
                            to="/departments"
                            sx={{
                                backgroundColor: location.pathname === '/departments' ? '#0077b6' : 'transparent',
                                '&:hover': { backgroundColor: '#005f8a' },
                            }}
                        >
                            <ListItemIcon sx={{ color: '#fff' }}><Work /></ListItemIcon>
                            <ListItemText primary={isOpen ? 'Departments' : ''} sx={{ color: '#fff' }} />
                        </ListItem>
                    )}

                    {/* HR + Admin: Salaries/Payroll */}
                    {(role === 'HR' || role === 'Admin') && (
                        <ListItem 
                            button 
                            component={Link} 
                            to="/payrolls"
                            sx={{
                                backgroundColor: location.pathname === '/payrolls' ? '#0077b6' : 'transparent',
                                '&:hover': { backgroundColor: '#005f8a' },
                            }}
                        >
                            <ListItemIcon sx={{ color: '#fff' }}><AttachMoney /></ListItemIcon>
                            <ListItemText primary={isOpen ? 'Salaries' : ''} sx={{ color: '#fff' }} />
                        </ListItem>
                    )}

                    {/* Admin Only: Users, Roles, Dashboard */}
                    {role === 'Admin' && (
                        <>
                            <Divider sx={{ backgroundColor: '#fff', opacity: 0.3 }} />
                            <ListItem 
                                button 
                                component={Link} 
                                to="/dashboard"
                                sx={{
                                    backgroundColor: location.pathname === '/dashboard' ? '#0077b6' : 'transparent',
                                    '&:hover': { backgroundColor: '#005f8a' },
                                }}
                            >
                                <ListItemIcon sx={{ color: '#fff' }}><Dashboard /></ListItemIcon>
                                <ListItemText primary={isOpen ? 'Dashboard' : ''} sx={{ color: '#fff' }} />
                            </ListItem>
                            <ListItem 
                                button 
                                component={Link} 
                                to="/users"
                                sx={{
                                    backgroundColor: location.pathname === '/users' ? '#0077b6' : 'transparent',
                                    '&:hover': { backgroundColor: '#005f8a' },
                                }}
                            >
                                <ListItemIcon sx={{ color: '#fff' }}><AccountBox /></ListItemIcon>
                                <ListItemText primary={isOpen ? 'Users' : ''} sx={{ color: '#fff' }} />
                            </ListItem>
                            <ListItem 
                                button 
                                component={Link} 
                                to="/roles"
                                sx={{
                                    backgroundColor: location.pathname === '/roles' ? '#0077b6' : 'transparent',
                                    '&:hover': { backgroundColor: '#005f8a' },
                                }}
                            >
                                <ListItemIcon sx={{ color: '#fff' }}><AdminPanelSettings /></ListItemIcon>
                                <ListItemText primary={isOpen ? 'Roles' : ''} sx={{ color: '#fff' }} />
                            </ListItem>
                        </>
                    )}

                    <Divider sx={{ backgroundColor: '#fff', opacity: 0.3 }} />

                    {/* Logout */}
                    <ListItem button onClick={() => {
                        localStorage.clear();
                        window.location.href = "/login";
                    }}>
                        <ListItemIcon sx={{ color: '#fff' }}><ExitToApp /></ListItemIcon>
                        <ListItemText primary={isOpen ? 'Logout' : ''} sx={{ color: '#fff' }} />
                    </ListItem>
                </List>
            </Box>
        </Drawer>
    );
};

export default Sidebar;
