import React, { useEffect, useState } from 'react';
import { Container, Grid, Typography, Box, TextField, MenuItem, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header component
import AttendanceGraph from '../components/AttendanceGraph';  // Import the Attendance Graph component
import AbsenceTypeChart from "../components/AbsenceTypeChart";  // Import Absence Type Chart component
import '../styles/Dashboard.css';
import AbsenceForm from "../components/AbsenceForm";  // Use the Dashboard CSS for Absence View

const AbsenceView = () => {
  const [absences, setAbsences] = useState([]);
  const [types, setTypes] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [filters, setFilters] = useState({ type: '', employeeId: '' });
  const [openForm, setOpenForm] = useState(false);
  const [selectedAbsence, setSelectedAbsence] = useState(null);

  // Temporary data for now until backend is ready
  useEffect(() => {
    setAbsences([
      { id: 1, employee: { firstName: "John", lastName: "Doe" }, type: "Sick", date: "2025-04-23", status: "Approved" },
      { id: 2, employee: { firstName: "Jane", lastName: "Doe" }, type: "Vacation", date: "2025-04-20", status: "Pending" },
    ]);
    setTypes(["Sick", "Vacation", "Personal"]);
    setEmployees([
      { id: 1, firstName: "John", lastName: "Doe" },
      { id: 2, firstName: "Jane", lastName: "Doe" },
    ]);
  }, []);

  const filteredAbsences = absences.filter(a =>
      (!filters.type || a.type === filters.type) &&
      (!filters.employeeId || a.employee.id === +filters.employeeId)
  );

  return (
      <div className="dashboard-page">
        {/* Sidebar and Header */}
        <Sidebar />
        <Header />

        {/* Main Content Area */}
        <Container className="dashboard-container">
          {/* Overview Section */}
          <Box className="overview-box">
            <Typography variant="h4" gutterBottom>
              Absence Records Overview
            </Typography>
            <Typography variant="h6" paragraph>
              Here's the current status of employee absences
            </Typography>

            {/* Filters */}
            <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
              <TextField
                  select label="Type" value={filters.type}
                  onChange={e => setFilters({ ...filters, type: e.target.value })}
                  size="small"
              >
                <MenuItem value="">All</MenuItem>
                {types.map(t => <MenuItem key={t} value={t}>{t}</MenuItem>)}
              </TextField>
              <TextField
                  select label="Employee" value={filters.employeeId}
                  onChange={e => setFilters({ ...filters, employeeId: e.target.value })}
                  size="small"
              >
                <MenuItem value="">All</MenuItem>
                {employees.map(emp => (
                    <MenuItem key={emp.id} value={emp.id}>
                      {emp.firstName} {emp.lastName}
                    </MenuItem>
                ))}
              </TextField>
              <Button variant="contained" onClick={() => setOpenForm(true)}>Add Absence</Button>
            </div>
          </Box>

          {/* Charts Section */}
          <Box className="charts-section" mt={4}>
            <Typography variant="h5" gutterBottom>
              Absence Type and Attendance Graph
            </Typography>
            <Grid container spacing={4}>
                {/* Attendance Graph Section */}
                <Box className="attendance-graph-box" mt={2}>
                  <Typography variant="h5" gutterBottom>
                    Attendance Trends (This Week)
                  </Typography>
                  <AttendanceGraph/>
                </Box>
              <Grid item xs={12} sm={6} md={6}>
                <Box sx={{ p: 2, boxShadow: 3 }}>
                  <AbsenceTypeChart /> {/* Absence Type Chart */}
                </Box>
              </Grid>
            </Grid>
          </Box>

          {/* Table Section */}
          <Box className="table-box" mt={4}>
            <Typography variant="h5" gutterBottom>
              Employee Absence Records
            </Typography>
            <TableContainer sx={{ marginTop: '2rem' }}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Employee</TableCell>
                    <TableCell>Type</TableCell>
                    <TableCell>Date</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell>Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {filteredAbsences.map(abs => (
                      <TableRow key={abs.id}>
                        <TableCell>{abs.employee.firstName} {abs.employee.lastName}</TableCell>
                        <TableCell>{abs.type}</TableCell>
                        <TableCell>{abs.date}</TableCell>
                        <TableCell>{abs.status}</TableCell>
                        <TableCell>
                          <Button size="small" onClick={() => {
                            setSelectedAbsence(abs);
                            setOpenForm(true);
                          }}>Edit</Button>
                        </TableCell>
                      </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </Container>

        {/* AbsenceForm Popup */}
        <AbsenceForm
            open={openForm}
            onClose={() => { setOpenForm(false); setSelectedAbsence(null); }}
            // onSave={handleSave}
            initialData={selectedAbsence}
            employees={employees}
            types={types}
        />
      </div>
  );
};

export default AbsenceView;
