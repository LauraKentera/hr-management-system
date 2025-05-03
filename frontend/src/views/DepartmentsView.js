import React, { useState, useEffect } from 'react';
import {
  Typography, Table, TableHead, TableRow, TableCell, TableBody,
  Button, Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Paper
} from '@mui/material';
import Sidebar from '../components/Sidebar';
import Header from '../components/Topbar';
import ApiEndpoints from "../api/ApiEndpoints";
// At the top of DepartmentsView.js
const API_BASE_URL = "http://localhost:8080/api/departments"; // Direct URL for certainty

const DepartmentsView = () => {
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [openAddModal, setOpenAddModal] = useState(false);
  const [openEditModal, setOpenEditModal] = useState(false);
  const [newDepartmentName, setNewDepartmentName] = useState('');
  const [managerId, setManagerId] = useState('');
  const [selectedDept, setSelectedDept] = useState(null);

  const API = '/api/departments';

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    try {
      setLoading(true);
      const response = await fetch(API_BASE_URL);
      
      if (!response.ok) throw new Error(`HTTP ${response.status}`);
      
      const data = await response.json();
      setDepartments(data);
    } catch (err) {
      console.error("Fetch error:", err);
      alert("Failed to load departments");
    } finally {
      setLoading(false);
    }
  };

  const handleAddDepartment = async () => {
    try {
      const payload = {
        name: newDepartmentName,
        managerId: managerId ? parseInt(managerId) : null  // Ensure proper number conversion
      };
  
      const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify(payload),
      });
  
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to add department');
      }
  
      const result = await response.json();
      console.log("Success:", result);
      
      setNewDepartmentName('');
      setManagerId('');
      setOpenAddModal(false);
      fetchDepartments(); // Refresh the list
    } catch (err) {
      console.error('API Error:', err);
      alert(`Error: ${err.message}`);
    }
  };

  const handleEditManager = () => {
    if (!selectedDept) return;

    const payload = {
      ...selectedDept,
      managerId: managerId ? parseInt(managerId) : null,
    };

    fetch(`${API}/${selectedDept.departmentId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
      .then((res) => {
        if (!res.ok) return res.text().then(text => { throw new Error(text); });
        return res.json();
      })
      .then(() => {
        setOpenEditModal(false);
        setSelectedDept(null);
        setManagerId('');
        fetchDepartments();
      })
      .catch((err) => alert(`Error updating manager: ${err.message}`));
  };

  const handleDelete = (id) => {
    if (!window.confirm('Are you sure you want to delete this department?')) return;

    fetch(`${API}/${id}`, {
      method: 'DELETE',
    })
      .then((res) => {
        if (!res.ok) throw new Error('Failed to delete department');
        fetchDepartments();
      })
      .catch((err) => alert(err.message));
  };

  if (loading) return <Typography>Loading...</Typography>;

  return (
    <div className="dashboard-page">
      <Sidebar />
      <Header />
      <div className="dashboard-container">
        <Typography variant="h4" gutterBottom>Departments</Typography>

        <Button variant="contained" color="success" onClick={() => setOpenAddModal(true)} sx={{ mb: 2 }}>
          Add Department
        </Button>

        <Paper sx={{ p: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Manager ID</TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {departments.map((dept) => (
                <TableRow key={dept.departmentId}>
                  <TableCell>{dept.departmentId}</TableCell>
                  <TableCell>{dept.name}</TableCell>
                  <TableCell>{dept.managerId || '—'}</TableCell>
                  <TableCell>
                    <Button
                      size="small"
                      variant="outlined"
                      color="primary"
                      onClick={() => {
                        setSelectedDept(dept);
                        setManagerId(dept.managerId || '');
                        setOpenEditModal(true);
                      }}
                      sx={{ mr: 1 }}
                    >
                      Edit Manager
                    </Button>
                    <Button
                      size="small"
                      variant="outlined"
                      color="error"
                      onClick={() => handleDelete(dept.departmentId)}
                    >
                      Delete
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>

        {/* Add Department Modal */}
        <Dialog open={openAddModal} onClose={() => setOpenAddModal(false)}>
          <DialogTitle>Add Department</DialogTitle>
          <DialogContent>
            <TextField
              label="Department Name"
              fullWidth
              margin="dense"
              value={newDepartmentName}
              onChange={(e) => setNewDepartmentName(e.target.value)}
            />
            <TextField
              label="Manager ID (optional)"
              fullWidth
              margin="dense"
              type="number"
              value={managerId}
              onChange={(e) => setManagerId(e.target.value)}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenAddModal(false)}>Cancel</Button>
            <Button variant="contained" onClick={handleAddDepartment}>Add</Button>
          </DialogActions>
        </Dialog>

        {/* Edit Manager Modal */}
        <Dialog open={openEditModal} onClose={() => setOpenEditModal(false)}>
          <DialogTitle>Edit Manager</DialogTitle>
          <DialogContent>
            <TextField
              label="New Manager ID"
              fullWidth
              margin="dense"
              type="number"
              value={managerId}
              onChange={(e) => setManagerId(e.target.value)}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenEditModal(false)}>Cancel</Button>
            <Button variant="contained" onClick={handleEditManager}>Save</Button>
          </DialogActions>
        </Dialog>
      </div>
    </div>
  );
};

export default DepartmentsView;
