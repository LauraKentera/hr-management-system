// src/pages/DepartmentView.js
import React, { useEffect, useState } from 'react';
import {
  Typography, Button, Paper, Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, IconButton
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import AddIcon from '@mui/icons-material/Add';
import departmentService from '../services/departmentService';
import employeeService from '../services/employeeService';
import DepartmentForm from '../components/DepartmentForm';

const DepartmentView = () => {
  const [departments, setDepartments] = useState([]);
  const [managers, setManagers] = useState([]);
  const [openForm, setOpenForm] = useState(false);
  const [selectedDepartment, setSelectedDepartment] = useState(null);

  const loadDepartments = async () => {
    const res = await departmentService.getDepartments();
    setDepartments(res.data);
  };

  const loadManagers = async () => {
    const res = await employeeService.getEmployees();
    setManagers(res.data);
  };

  useEffect(() => {
    loadDepartments();
    loadManagers();
  }, []);

  const handleEdit = (dep) => {
    setSelectedDepartment(dep);
    setOpenForm(true);
  };

  const handleClose = () => {
    setOpenForm(false);
    setSelectedDepartment(null);
  };

  const handleSave = async () => {
    await loadDepartments();
    handleClose();
  };

  return (
    <Paper sx={{ p: 3 }}>
      <Typography variant="h5" gutterBottom>Departments</Typography>
      <Button variant="contained" startIcon={<AddIcon />} onClick={() => setOpenForm(true)} sx={{ mb: 2 }}>
        Add Department
      </Button>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Department Name</TableCell>
              <TableCell>Manager</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {departments.map(dep => (
              <TableRow key={dep.id}>
                <TableCell>{dep.name}</TableCell>
                <TableCell>
                  {dep.manager ? `${dep.manager.firstName} ${dep.manager.lastName}` : '—'}
                </TableCell>
                <TableCell align="right">
                  <IconButton onClick={() => handleEdit(dep)}><EditIcon /></IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <DepartmentForm
        open={openForm}
        onClose={handleClose}
        onSave={handleSave}
        initialData={selectedDepartment}
        managers={managers}
      />
    </Paper>
  );
};

export default DepartmentView;
