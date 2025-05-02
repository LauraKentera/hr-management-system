import React from "react";
import Sidebar from "./Sidebar";
import { Box, AppBar, Toolbar, Typography } from "@mui/material";

const Layout = ({ children }) => {
    return (
        <Box sx={{ display: 'flex' }}>
            {/* Sidebar */}
            <Sidebar />

            {/* Main content */}
            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    bgcolor: 'background.default',
                    p: 3,
                    marginLeft: '240px', // Adjust depending on your sidebar width
                }}
            >
                <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                    <Toolbar>
                        <Typography variant="h6">User Management</Typography>
                    </Toolbar>
                </AppBar>

                {/* Children will be passed here (e.g., UsersPage) */}
                <Box sx={{ marginTop: '64px' }}>
                    {children}
                </Box>
            </Box>
        </Box>
    );
};

export default Layout;
