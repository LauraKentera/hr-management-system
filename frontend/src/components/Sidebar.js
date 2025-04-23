import React, { useState } from 'react';
import { Box, Drawer, List, ListItem, ListItemIcon, ListItemText, Divider, IconButton } from '@mui/material';
import { People, Work, AccountBox, ExitToApp, ChevronLeft, ChevronRight } from '@mui/icons-material';  // Add icons for collapsing

const Sidebar = () => {
    const [isOpen, setIsOpen] = useState(true);  // State to control sidebar visibility

    const toggleSidebar = () => {
        setIsOpen(!isOpen);  // Toggle sidebar open/close
    };

    return (
        <Drawer
            sx={{
                width: isOpen ? 240 : 60,  // Collapsed width is 60px
                flexShrink: 0,
                '& .MuiDrawer-paper': {
                    width: isOpen ? 240 : 60,  // Set the width based on state
                    boxSizing: 'border-box',
                    transition: 'width 0.3s',  // Smooth transition for collapsing
                },
            }}
            variant="permanent"
            anchor="left"
        >
            <Box sx={{ width: '100%', height: '100%' }}>
                <List>
                    <ListItem button onClick={toggleSidebar}>
                        <ListItemIcon>
                            {isOpen ? <ChevronLeft /> : <ChevronRight />}  {/* Toggle icon */}
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Collapse' : ''} />
                    </ListItem>

                    <ListItem button>
                        <ListItemIcon>
                            <People />
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Employees' : ''} />
                    </ListItem>

                    <ListItem button>
                        <ListItemIcon>
                            <Work />
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Departments' : ''} />
                    </ListItem>

                    <ListItem button>
                        <ListItemIcon>
                            <AccountBox />
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Positions' : ''} />
                    </ListItem>

                    <Divider />

                    <ListItem button>
                        <ListItemIcon>
                            <ExitToApp />
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Logout' : ''} />
                    </ListItem>
                </List>
            </Box>
        </Drawer>
    );
};

export default Sidebar;
