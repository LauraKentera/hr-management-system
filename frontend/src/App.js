import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import DashboardPage from './views/DashboardPage';  // Correct path for DashboardPage
import LoginView from './views/LoginView';  // Correct path for LoginView

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginView />} />  {/* Login route */}
                <Route path="/dashboard" element={<DashboardPage />} />  {/* Dashboard route */}
                <Route path="/" element={<LoginView />} />  {/* Default route */}
            </Routes>
        </Router>
    );
}

export default App;
