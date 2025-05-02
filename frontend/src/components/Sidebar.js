import React, {useState} from 'react';
import {Box, Drawer, List, ListItem, ListItemIcon, ListItemText, Divider, IconButton} from '@mui/material';
import {
    People,
    Work,
    AccountBox,
    ExitToApp,
    ChevronLeft,
    ChevronRight,
    AttachMoney,
    CalendarToday,
    InsertChart
} from '@mui/icons-material';  // Add icons
import {Link} from 'react-router-dom'; // Import Link

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
                    position: 'fixed',  // Fix sidebar position
                    top: '90px',  // Adjust this value based on your top bar height (e.g., 64px for Material UI's AppBar height)
                    height: 'calc(100% - 64px)',  // Make sidebar full height minus top bar height
                },
            }}
            variant="permanent"
            anchor="left"
        >
            <Box sx={{width: '100%', height: '100%'}}>
                <List>
                    {/* Toggle Sidebar Button */}
                    <ListItem button onClick={toggleSidebar}>
                        <ListItemIcon>
                            {isOpen ? <ChevronLeft/> : <ChevronRight/>} {/* Toggle icon */}
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Collapse' : ''}/>
                    </ListItem>

                    {/* Absences Section */}
                    <ListItem button component={Link} to="/absences"> {/* Use Link to navigate */}
                        <ListItemIcon>
                            <CalendarToday/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Absences' : ''}/>
                    </ListItem>

                    <Divider/>

                    {/* Benefits Section */}
                    <ListItem button component={Link} to="/benefits"> {/* Use Link to navigate */}
                        <ListItemIcon>
                            <AttachMoney/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Benefits' : ''}/>
                    </ListItem>


                    <Divider/>

                    {/* Contracts Section */}
                    <ListItem button component={Link} to="/contracts">
                        <ListItemIcon>
                            <InsertChart/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Contracts' : ''}/>
                    </ListItem>

                    <Divider/>

                    {/* Employees Section */}
                    <ListItem button component={Link} to="/employees">
                        <ListItemIcon>
                            <People/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Employees' : ''}/>
                    </ListItem>


                    <Divider/>

                    {/* Departments Section */}
                    <ListItem button component={Link} to="/departments">
                        <ListItemIcon>
                            <Work/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Departments' : ''}/>
                    </ListItem>

                    <Divider/>

                    {/* Salaries Section */}
                    <ListItem button component={Link} to="/salaries">
                        <ListItemIcon>
                            <AttachMoney/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Salaries' : ''}/>
                    </ListItem>


                    <Divider/>

                    {/* Logout Section */}
                    <ListItem button>
                        <ListItemIcon>
                            <ExitToApp/>
                        </ListItemIcon>
                        <ListItemText primary={isOpen ? 'Logout' : ''}/>
                    </ListItem>
                </List>
            </Box>
        </Drawer>
    );
};

export default Sidebar;
