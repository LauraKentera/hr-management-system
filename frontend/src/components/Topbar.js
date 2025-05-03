import React from 'react';
import { AppBar, Toolbar, Typography, Box } from '@mui/material';

const Topbar = () => {
    return (
        <AppBar
            position="fixed"
            sx={{
                zIndex: (theme) => theme.zIndex.drawer + 1,
                backgroundColor: '#004e92',
                boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.15)',
            }}
        >
            <Toolbar sx={{ justifyContent: 'space-between' }}>
                <Box sx={{ flexGrow: 1 }}>
                    <Typography variant="h6" sx={{ fontWeight: 600, color: '#fff' }}>
                        HR Management System
                    </Typography>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Topbar;
