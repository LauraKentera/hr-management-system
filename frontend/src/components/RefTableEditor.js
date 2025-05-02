import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Table, TableBody, TableCell, TableHead, TableRow, Button, Typography, Paper,
  Dialog, DialogTitle, DialogContent, DialogActions, TextField, Snackbar
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';

import Sidebar from '../components/Sidebar';
import Header from '../components/Topbar';
import ApiEndpoints from '../api/ApiEndpoints';
import '../styles/Dashboard.css';

const RefTableEditor = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [snackbar, setSnackbar] = useState('');
  const [openDialog, setOpenDialog] = useState(false);
  const [editing, setEditing] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetch(ApiEndpoints.employee.getAll)
      .then((res) => {
        if (!res.ok) throw new Error(`Failed to fetch employees: ${res.status}`);
        return res.json();
      })
      .then(setEmployees)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const handleViewDetails = (id) => {
    navigate(`/employees/${id}`);
  };

  const handleDelete = (id) => {
    if (!window.confirm('Are you sure you want to delete this employee?')) return;
    fetch(`${ApiEndpoints.employee.base}/${id}`, { method: 'DELETE' })
      .then((res) => {
        if (!res.ok) throw new Error('Failed to delete employee');
        setEmployees(employees.filter(emp => emp.id !== id));
        setSnackbar('Employee deleted successfully');
      })
      .catch(err => setError(`Error: ${err.message}`));
  };

  const handleSubmit = (values, { setSubmitting, resetForm }) => {
    const payload = {
      ...values,
      department: { name: values.department }
    };

    const method = editing ? 'PUT' : 'POST';
    const url = editing
      ? ApiEndpoints.employee.update(editing.id)
      : ApiEndpoints.employee.create;

    fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
      .then(res => {
        if (!res.ok) throw new Error('Failed to save employee');
        return res.json();
      })
      .then((data) => {
        if (editing) {
          setEmployees(prev => prev.map(emp => emp.id === data.id ? data : emp));
        } else {
          setEmployees(prev => [...prev, data]);
        }
        setOpenDialog(false);
        setEditing(null);
        resetForm();
        setSnackbar(`Employee ${editing ? 'updated' : 'added'} successfully`);
      })
      .catch(err => setError(`Error: ${err.message}`))
      .finally(() => setSubmitting(false));
  };


  if (loading) return <Typography>Loading...</Typography>;
  if (error) return <Typography color="error">{error}</Typography>;

  return (
    <div className="dashboard-page">
      <Sidebar />
      <Header />
      <div className="dashboard-container">
        <Typography variant="h4" gutterBottom>Employees</Typography>

        <Button
          variant="contained"
          color="success"
          onClick={() => { setEditing(null); setOpenDialog(true); }}
          sx={{ mb: 2 }}
        >
          Add Employee
        </Button>

        <Snackbar
          open={!!snackbar}
          autoHideDuration={4000}
          onClose={() => setSnackbar('')}
          message={snackbar}
        />

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
                    <Button variant="contained" size="small" sx={{ mr: 1 }} onClick={() => handleViewDetails(employee.id)}>View</Button>
                    <Button variant="outlined" size="small" sx={{ mr: 1 }} onClick={() => { setEditing(employee); setOpenDialog(true); }}>Edit</Button>
                    <Button variant="outlined" color="error" size="small" onClick={() => handleDelete(employee.id)}>Delete</Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>

        {/* Dialog for Add/Edit */}
        <Dialog open={openDialog} onClose={() => setOpenDialog(false)} fullWidth>
          <DialogTitle>{editing ? 'Edit Employee' : 'Add Employee'}</DialogTitle>
          <Formik
            initialValues={{
              firstName: editing?.firstName || '',
              lastName: editing?.lastName || '',
              email: editing?.email || '',
              department: editing?.department?.name || '',
              employmentStatus: editing?.employmentStatus || ''
            }}
            validationSchema={Yup.object({
              firstName: Yup.string().required('Required'),
              lastName: Yup.string().required('Required'),
              email: Yup.string().email('Invalid email').required('Required'),
              department: Yup.string().required('Required'),
              employmentStatus: Yup.string().required('Required')
            })}
            onSubmit={handleSubmit}
            enableReinitialize
          >
            {({ values, handleChange, errors, touched, isSubmitting }) => (
              <Form>
                <DialogContent>
                  {['firstName', 'lastName', 'email', 'department', 'employmentStatus'].map(field => (
                    <TextField
                      key={field}
                      label={field.replace(/([A-Z])/g, ' $1')}
                      name={field}
                      fullWidth
                      margin="dense"
                      value={values[field]}
                      onChange={handleChange}
                      error={touched[field] && Boolean(errors[field])}
                      helperText={touched[field] && errors[field]}
                    />
                  ))}
                </DialogContent>
                <DialogActions>
                  <Button onClick={() => setOpenDialog(false)}>Cancel</Button>
                  <Button type="submit" variant="contained" disabled={isSubmitting}>
                    {editing ? 'Update' : 'Add'}
                  </Button>
                </DialogActions>
              </Form>
            )}
          </Formik>
        </Dialog>
      </div>
    </div>
  );
};

export default RefTableEditor;
