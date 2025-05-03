import React from 'react';
import { AppBar, Toolbar, Typography, IconButton, Box, Avatar, Menu, MenuItem } from '@mui/material';
import { AccountCircle, ExitToApp } from '@mui/icons-material'; // Importing Material Icons

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
        <AppBar 
            position="fixed" 
            sx={{
                zIndex: (theme) => theme.zIndex.drawer + 1, 
                backgroundColor: '#004e92',  // Deep blue for consistency with sidebar
                boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.15)',  // Subtle shadow for depth
            }}
        >
            <Toolbar sx={{ justifyContent: 'space-between' }}>
                <Box sx={{ flexGrow: 1 }}>
                    <Typography variant="h6" sx={{ fontWeight: 600, color: '#fff' }}>
                        HR Management System
                    </Typography>
                </Box>

                {/* User Icon and Menu */}
                <IconButton 
                    color="inherit" 
                    onClick={handleMenuOpen} 
                    sx={{ 
                        '&:hover': { backgroundColor: 'rgba(255, 255, 255, 0.1)' }, // Hover effect
                        borderRadius: '50%' 
                    }}
                >
                    <Avatar 
                        alt="User Profile" 
                        src="/static/images/avatar/1.jpg" 
                        sx={{ width: 36, height: 36 }} 
                    />
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
                    <MenuItem onClick={handleMenuClose} sx={{ color: '#004e92' }}>
                        Profile
                    </MenuItem>
                    <MenuItem onClick={handleLogout} sx={{ color: '#004e92' }}>
                        <ExitToApp sx={{ marginRight: '8px' }} />
                        Logout
                    </MenuItem>
                </Menu>
            </Toolbar>
        </AppBar>
    );
};

export default Topbar;
