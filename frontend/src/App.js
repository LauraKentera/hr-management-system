import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginForm from "./views/LoginView"; // Adjust path if needed
import Dashboard from "./views/DashboardPage"; // Replace with your default logged-in page
import AbsenceView from "./views/AbsenceView";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/absences" element={<AbsenceView />} />
                {/* Add other routes here */}
            </Routes>
        </Router>
    );
}

export default App;
