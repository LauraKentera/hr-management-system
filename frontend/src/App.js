import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import DashboardPage from './views/DashboardPage';  // Correct path for DashboardPage
import LoginView from './views/LoginView';
import AbsenceView from "./views/AbsenceView";
import BenefitList from "./views/BenefitList";
import ContractsView from "./views/ContractsView";
import EmployeeView from "./views/EmployeeView";
import DepartmentsView from "./views/DepartmentsView";
import PayrollView from "./views/PayrollView";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginView />} />  {/* Login route */}
                <Route path="/dashboard" element={<DashboardPage />} />  {/* Dashboard route */}
                <Route path="/" element={<LoginView />} />  {/* Default route */}
                <Route path="/absence" element={<AbsenceView />} /> {/* Route for AbsenceView */}
                <Route path="/benefits" element={<BenefitList />} /> {/* Route for AbsenceView */}
                <Route path="/contracts" element={<ContractsView />} />
                <Route path="/employees" element={<EmployeeView />} />
                <Route path="/departments" element={<DepartmentsView />} />
                <Route path="/salaries" element={<PayrollView />} />
            </Routes>
        </Router>
    );
}

export default App;