import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

// Import your components (to be created later)
import DashboardView from './views/DashboardView';
import LoginView from './views/LoginView';
import ProfileView from './views/ProfileView';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<DashboardView />} />
                <Route path="/login" element={<LoginView />} />
                <Route path="/profile" element={<ProfileView />} />
            </Routes>
        </Router>
    );
}

export default App;
