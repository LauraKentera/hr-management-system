import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Topbar from './components/Topbar';
import Sidebar from './components/Sidebar';
import DashboardView from './views/DashboardView';
import LoginView from './views/LoginView';
import ProfileView from './views/ProfileView';
import ContractsView from './views/ContractsView';
import PositionView from './views/PositionView';

function App() {
  return (
    <Router>
      <Topbar />
      <div className="app-container">
        <Sidebar />
        <main>
          <Routes>
            <Route path="/login" element={<LoginView />} />
            <Route path="/profile" element={<ProfileView />} />
            <Route path="/dashboard" element={<DashboardView />} />
            <Route path="/contracts" element={<ContractsView />} />
            <Route path="/positions" element={<PositionView />} />
            <Route path="*" element={<Navigate to="/dashboard" replace />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
