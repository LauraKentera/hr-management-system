import React from 'react';
import { NavLink } from 'react-router-dom';

const Sidebar = () => (
  <nav className="sidebar">
    <ul>
      <li><NavLink to="/dashboard">Dashboard</NavLink></li>
      <li><NavLink to="/contracts">Contracts</NavLink></li>
      <li><NavLink to="/positions">Positions</NavLink></li>
      <li><NavLink to="/profile">Profile</NavLink></li>
    </ul>
  </nav>
);

export default Sidebar;
