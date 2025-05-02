import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginForm from "./views/LoginView"; // Adjust path if needed
import Dashboard from "./views/DashboardPage"; // Replace with your default logged-in page

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/dashboard" element={<Dashboard />} />
                {/* Add other routes here */}
            </Routes>
        </Router>
    );
}

export default App;
