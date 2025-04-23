import React from 'react';
import { Link } from 'react-router-dom';
import { logout } from '../services/authService';  // Add this line

function Sidebar() {
    return (
        <div className="sidebar">
            <Link to="/dashboard">Dashboard</Link>
            <Link to="/profile">Profile</Link>
            <button onClick={logout}>Logout</button>
        </div>
    );
}

export default Sidebar;
