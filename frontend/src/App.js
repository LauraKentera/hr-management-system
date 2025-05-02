import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { SnackbarProvider } from "notistack"; // Import SnackbarProvider
import Dashboard from "./views/DashboardPage";
import UsersPage from "./views/UsersPage";
import RolesPage from "./views/RolesPage";
import EmployeesPage from "./views/EmployeesPage";
import AbsencePage from "./views/AbsenceView";
import LoginView from "./views/LoginView";
import ProtectedRoute from "./components/ProtectedRoute";
import Layout from "./components/Layout";

function App() {
    return (
        <SnackbarProvider maxSnack={3}>  {/* Wrap the app with SnackbarProvider */}
            <Router>
                <Routes>
                    {/* Public Route */}
                    <Route path="/login" element={<LoginView />} />

                    {/* Protected Routes */}
                    <Route
                        path="/dashboard"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR", "Employee"]}>
                                <Layout>
                                    <Dashboard />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/users"
                        element={
                            <ProtectedRoute allowedRoles={["Admin"]}>
                                <Layout>
                                    <UsersPage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/roles"
                        element={
                            <ProtectedRoute allowedRoles={["Admin"]}>
                                <Layout>
                                    <RolesPage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/employees"
                        element={
                            <ProtectedRoute allowedRoles={["HR", "Admin"]}>
                                <Layout>
                                    <EmployeesPage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    {/* Absences route accessible by HR, Admin, and Employee */}
                    <Route
                        path="/absences"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR", "Employee"]}>
                                <Layout>
                                    <AbsencePage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/absences"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR", "Employee"]}>
                                <Layout>
                                    <AbsencePage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    {/* Add more routes here as necessary */}

                </Routes>
            </Router>
        </SnackbarProvider>
    );
}

export default App;