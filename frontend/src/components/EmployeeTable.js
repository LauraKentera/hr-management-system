import React, { useState, useEffect } from 'react';
import { Button, Box, Grid, Typography, Paper } from '@mui/material';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header (Topbar) component
import PayrollForm from '../components/PayrollForm';  // Import PayrollForm component
import SalaryByDepartmentChart from '../components/SalaryByDepartmentChart'; // Import SalaryByDepartmentChart
import PayrollExpensesChart from '../components/PayrollExpensesChart'; // Import PayrollExpensesChart
import EmployeeTable from '../components/EmployeeTable';  // Import EmployeeTable
import '../styles/Dashboard.css';  // Ensure Dashboard CSS is applied

const PayrollView = () => {
  const [entries, setEntries] = useState([]);
  const [filterStatus, setFilterStatus] = useState('All');
  const [showForm, setShowForm] = useState(false);

  // Dummy payroll entries
  const loadPayrolls = () => {
    const dummyData = [
      { id: 1, employee: { name: 'John Doe' }, baseSalary: 50000, benefits: 5000, deductions: 2000, status: 'Paid' },
      { id: 2, employee: { name: 'Jane Smith' }, baseSalary: 55000, benefits: 4000, deductions: 1500, status: 'Pending' },
      { id: 3, employee: { name: 'Alex Johnson' }, baseSalary: 60000, benefits: 6000, deductions: 3000, status: 'Paid' },
    ];
    setEntries(dummyData);
  };

  useEffect(() => {
    loadPayrolls();  // Use dummy data for now
  }, []);

  const filtered = entries.filter(
      (entry) => filterStatus === 'All' || entry.status === filterStatus
  );

  const calcNetPay = (entry) =>
      entry.baseSalary + (entry.benefits || 0) - (entry.deductions || 0);

  return (
      <div className="dashboard-page">
        {/* Sidebar and Topbar */}
        <Sidebar />
        <Header />

        {/* Main Content Area */}
        <div className="dashboard-container">
          <Typography variant="h4" gutterBottom>
            Payroll
          </Typography>

          {/* Add Payroll Button and Status Filter */}
          <Box sx={{ marginBottom: '1rem' }}>
            <Button variant="contained" onClick={() => setShowForm(true)} sx={{ mr: 2 }}>
              + Add Payroll Entry
            </Button>
            <label style={{ marginLeft: '1rem' }}>Status Filter: </label>
            <select value={filterStatus} onChange={(e) => setFilterStatus(e.target.value)}>
              <option value="All">All</option>
              <option value="Paid">Paid</option>
              <option value="Pending">Pending</option>
            </select>
          </Box>

          {/* Payroll Form Modal */}
          {showForm && (
              <PayrollForm
                  onClose={() => setShowForm(false)}
                  onSubmitSuccess={loadPayrolls}
              />
          )}

          {/* Payroll Charts and Table Section */}
          <Grid container spacing={4}>
            {/* Salary by Department Chart */}
            <Grid item xs={12} md={6}>
              <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
                <Typography variant="h6" gutterBottom>
                  Salary by Department
                </Typography>
                <SalaryByDepartmentChart />
              </Paper>
            </Grid>

            {/* Payroll Expenses Chart */}
            <Grid item xs={12} md={6}>
              <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
                <Typography variant="h6" gutterBottom>
                  Payroll Expenses
                </Typography>
                <PayrollExpensesChart />
              </Paper>
            </Grid>

            {/* Payroll Entries Table */}
            <Grid item xs={12} md={8}>
              <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
                <Typography variant="h6" gutterBottom>
                  Payroll Entries
                </Typography>
                <EmployeeTable />
              </Paper>
            </Grid>
          </Grid>
        </div>
      </div>
  );
};

export default PayrollView;
