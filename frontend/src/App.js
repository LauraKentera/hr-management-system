import React from 'react';
import {BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import Topbar from './components/Topbar';
import Sidebar from './components/Sidebar';
import DashboardView from './views/DashboardView';
import LoginView from './views/LoginView';
import ProfileView from './views/ProfileView';
import ContractsView from './views/ContractsView';
import PositionView from './views/PositionView';
import AbsenceView from './views/AbsenceView'
import DepartmentView from './views/DepartmentView';
import EmployeeView from './views/EmployeeView';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import DashboardPage from './views/DashboardPage';  // Correct path for DashboardPage
import LoginView from './views/LoginView';  // Correct path for LoginView

function App() {
    return (

        <Router>
            <Routes>
                <Route path="/login" element={<LoginView/>}/> {/* Login route */}
                <Route path="/dashboard" element={<DashboardPage/>}/> {/* Dashboard route */}
                <Route path="/" element={<LoginView/>}/> {/* Default route */}
            </Routes>
        </Router>)
}

export default App;
