import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { SnackbarProvider } from "notistack"; // Import SnackbarProvider
import Dashboard from "./views/DashboardPage";
import UsersPage from "./views/UsersPage";
import RolesPage from "./views/RolesPage";
import AbsencePage from "./views/AbsenceView";
import LoginView from "./views/LoginView";
import ProtectedRoute from "./components/ProtectedRoute";
import Layout from "./components/Layout";
import EmployeesView from './views/EmployeePage';
import PayrollPage from "./views/PayrollPage";
import BenefitsListPage from "./views/BenefitListPage";
import DepartmentsView from './views/DepartmentsView';
import ContractListPage from './views/ContractList';  // Import Contract List page
import ContractFormPage from './views/ContractFormPage';
import EmployeePage from "./views/EmployeePage"; // Import Contract Form page

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
                            <ProtectedRoute allowedRoles={["Admin"]}>
                                <Layout>
                                    <EmployeePage />
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

                    {/* Contracts Route - Accessible by Admin and HR */}
                    <Route
                        path="/contracts"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR"]}>
                                <Layout>
                                    <ContractListPage /> {/* Contract List page */}
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    {/* Create Contract Route - Accessible by Admin and HR */}
                    <Route
                        path="/contracts/create"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR"]}>
                                <Layout>
                                    <ContractFormPage /> {/* Contract Form for creating */}
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    {/* Edit Contract Route - Accessible by Admin and HR */}
                    <Route
                        path="/contracts/edit/:id"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR"]}>
                                <Layout>
                                    <ContractFormPage /> {/* Contract Form for editing */}
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    {/* Payroll and Benefits routes */}
                    <Route
                        path="/payrolls"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR"]}>
                                <Layout>
                                    <PayrollPage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/benefits"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR", "Employee"]}>
                                <Layout>
                                    <BenefitsListPage />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/departments"
                        element={
                            <ProtectedRoute allowedRoles={["Admin", "HR"]}>
                                <Layout>
                                    <DepartmentsView />
                                </Layout>
                            </ProtectedRoute>
                        }
                    />

                </Routes>
            </Router>
        </SnackbarProvider >
    );
}

export default App;
