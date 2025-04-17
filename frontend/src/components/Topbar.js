import React from 'react';
import { logout } from '../services/authService';  // Add this line

function Topbar() {
    return (
        <div className="topbar">
            <h1>HR Dashboard</h1>
            <button onClick={logout}>Logout</button>
        </div>
    );
}

export default Topbar;
