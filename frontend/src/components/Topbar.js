import React from 'react';
import { AppBar, Toolbar, Typography, IconButton, Box, Avatar, Menu, MenuItem } from '@mui/material';
import { AccountCircle, ExitToApp } from '@mui/icons-material';  // Importing Material Icons

const Topbar = () => {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const openMenu = Boolean(anchorEl);

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        // Perform logout action here
        console.log('Logging out...');
        // You can also remove the token from localStorage if you're using JWT
        localStorage.removeItem('token');
    };

    return (
        <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
            <Toolbar>
                <Box sx={{ flexGrow: 1 }}>
                    <Typography variant="h6">
                        HR Management System
                    </Typography>
                </Box>

                {/* User Icon and Menu */}
                <IconButton color="inherit" onClick={handleMenuOpen}>
                    <Avatar alt="User Profile" src="/static/images/avatar/1.jpg" />
                </IconButton>

                {/* Profile Menu */}
                <Menu
                    anchorEl={anchorEl}
                    open={openMenu}
                    onClose={handleMenuClose}
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                >
                    <MenuItem onClick={handleMenuClose}>Profile</MenuItem>
                    <MenuItem onClick={handleLogout}>
                        <ExitToApp sx={{ marginRight: '8px' }} />
                        Logout
                    </MenuItem>
                </Menu>
            </Toolbar>
        </AppBar>
    );
};

export default Topbar;
