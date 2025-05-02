import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Table, TableBody, TableCell, TableHead, TableRow, Button, Typography, Paper,
  Dialog, DialogTitle, DialogContent, DialogActions, TextField
} from '@mui/material';
import Sidebar from '../components/Sidebar';
import Header from '../components/Topbar';
import ApiEndpoints from '../api/ApiEndpoints';
import '../styles/Dashboard.css';

const EmployeesView = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [openAddModal, setOpenAddModal] = useState(false);
  const [newEmployee, setNewEmployee] = useState({
    pin: '',
    firstName: '',
    lastName: '',
    birthDate: '',
    email: '',
    departmentId: '',
    positionId: '',
    employmentStatus: '',
  });

  const navigate = useNavigate();

  useEffect(() => {
    fetch(ApiEndpoints.employee.getAll)
      .then((res) => {
        if (!res.ok) throw new Error(`Failed to fetch employees: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        setEmployees(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  const handleViewDetails = (id) => {
    navigate(`/employees/${id}`);
  };

  const handleDelete = (id) => {
    if (!window.confirm('Are you sure you want to delete this employee?')) return;

    fetch(ApiEndpoints.employee.delete(id), {
      method: 'DELETE',
    })
      .then((res) => {
        if (!res.ok) throw new Error('Failed to delete employee');
        setEmployees(employees.filter((emp) => emp.id !== id));
      })
      .catch((err) => {
        alert(`Error deleting employee: ${err.message}`);
      });
  };

  const handleAddEmployee = () => {
    const payload = {
      pin: newEmployee.pin,
      firstName: newEmployee.firstName,
      lastName: newEmployee.lastName,
      birthDate: newEmployee.birthDate,
      dateOfHire: new Date().toISOString().split('T')[0],
      email: newEmployee.email,
      gender: 'Other',
      nationality: { nationalityId: 1 },
      department: { departmentId: parseInt(newEmployee.departmentId) },
      position: { positionId: parseInt(newEmployee.positionId) },
      employmentStatus: newEmployee.employmentStatus,
      employmentType: 'Full-Time',
      taxId: 'TEMP-TAX',
    };

    fetch(ApiEndpoints.employee.create, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
      .then((res) => {
        if (!res.ok) {
          return res.text().then(text => {
            throw new Error(text || 'Failed to create employee');
          });
        }
        return res.json();
      })
      .then((created) => {
        setEmployees([...employees, created]);
        setOpenAddModal(false);
        setNewEmployee({
          pin: '',
          firstName: '',
          lastName: '',
          birthDate: '',
          email: '',
          departmentId: '',
          positionId: '',
          employmentStatus: '',
        });
      })
      .catch((err) => {
        alert(`Error creating employee:\n${err.message}`);
      });
  };

  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography color="error">{error}</Typography>;

  return (
    <div className="dashboard-page">
      <Sidebar />
      <Header />
      <div className="dashboard-container">
        <Typography variant="h4" gutterBottom>Employees</Typography>

        <Button variant="contained" color="success" onClick={() => setOpenAddModal(true)} sx={{ mb: 2 }}>
          Add Employee
        </Button>

        <Paper sx={{ p: 3, boxShadow: 3, borderRadius: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Email</TableCell>
                <TableCell>Department</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {employees.map((employee) => (
                <TableRow key={employee.id}>
                  <TableCell>{employee.id}</TableCell>
                  <TableCell>{`${employee.firstName} ${employee.lastName}`}</TableCell>
                  <TableCell>{employee.email}</TableCell>
                  <TableCell>{employee.department?.name || 'N/A'}</TableCell>
                  <TableCell>{employee.employmentStatus}</TableCell>
                  <TableCell>
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() => handleViewDetails(employee.id)}
                      sx={{ mr: 1 }}
                    >
                      View
                    </Button>
                    <Button
                      variant="outlined"
                      color="error"
                      onClick={() => handleDelete(employee.id)}
                    >
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>

        {/* Add Employee Modal */}
        <Dialog open={openAddModal} onClose={() => setOpenAddModal(false)}>
          <DialogTitle>Add New Employee</DialogTitle>
          <DialogContent>
            <TextField
              label="PIN"
              fullWidth
              margin="dense"
              value={newEmployee.pin}
              onChange={(e) => setNewEmployee({ ...newEmployee, pin: e.target.value })}
            />
            <TextField
              label="First Name"
              fullWidth
              margin="dense"
              value={newEmployee.firstName}
              onChange={(e) => setNewEmployee({ ...newEmployee, firstName: e.target.value })}
            />
            <TextField
              label="Last Name"
              fullWidth
              margin="dense"
              value={newEmployee.lastName}
              onChange={(e) => setNewEmployee({ ...newEmployee, lastName: e.target.value })}
            />
            <TextField
              label="Birth Date (YYYY-MM-DD)"
              fullWidth
              margin="dense"
              value={newEmployee.birthDate}
              onChange={(e) => setNewEmployee({ ...newEmployee, birthDate: e.target.value })}
            />
            <TextField
              label="Email"
              fullWidth
              margin="dense"
              value={newEmployee.email}
              onChange={(e) => setNewEmployee({ ...newEmployee, email: e.target.value })}
            />
            <TextField
              label="Department ID"
              fullWidth
              margin="dense"
              value={newEmployee.departmentId}
              onChange={(e) => setNewEmployee({ ...newEmployee, departmentId: e.target.value })}
            />
            <TextField
              label="Position ID"
              fullWidth
              margin="dense"
              value={newEmployee.positionId}
              onChange={(e) => setNewEmployee({ ...newEmployee, positionId: e.target.value })}
            />
            <TextField
              select
              label="Employment Status"
              fullWidth
              margin="dense"
              value={newEmployee.employmentStatus}
              onChange={(e) => setNewEmployee({ ...newEmployee, employmentStatus: e.target.value })}
              SelectProps={{ native: true }}
            >
              <option value="">-- Select Status --</option>
              <option value="Active">Active</option>
              <option value="On Leave">On Leave</option>
              <option value="Terminated">Terminated</option>
            </TextField>

          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenAddModal(false)}>Cancel</Button>
            <Button variant="contained" onClick={handleAddEmployee}>Add</Button>
          </DialogActions>
        </Dialog>
      </div>
    </div>
  );
};

export default EmployeesView;