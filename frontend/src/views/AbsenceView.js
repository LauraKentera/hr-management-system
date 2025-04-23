
import React, { useEffect, useState } from 'react';
import {
  Paper, Typography, TextField, MenuItem, Button,
  Table, TableBody, TableCell, TableContainer, TableHead, TableRow,
} from '@mui/material';
import AbsenceForm from '../components/AbsenceForm';
import absenceService from '../services/absenceService';
import employeeService from '../services/employeeService';

const AbsenceView = () => {
  const [absences, setAbsences] = useState([]);
  const [types, setTypes] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [filters, setFilters] = useState({ type: '', employeeId: '' });
  const [openForm, setOpenForm] = useState(false);
  const [selectedAbsence, setSelectedAbsence] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    const [absenceRes, typeRes, empRes] = await Promise.all([
      absenceService.getAbsences(),
      absenceService.getAbsenceTypes(),
      employeeService.getEmployees(),
    ]);
    setAbsences(absenceRes.data);
    setTypes(typeRes.data);
    setEmployees(empRes.data);
  };

  const filteredAbsences = absences.filter(a =>
    (!filters.type || a.type === filters.type) &&
    (!filters.employeeId || a.employee.id === +filters.employeeId)
  );

  return (
    <Paper sx={{ p: 3 }}>
      <Typography variant="h5" gutterBottom>Absence Records</Typography>
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

      <TableContainer>
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

      <AbsenceForm
        open={openForm}
        onClose={() => { setOpenForm(false); setSelectedAbsence(null); }}
        onSave={fetchData}
        initialData={selectedAbsence}
        employees={employees}
        types={types}
      />
    </Paper>
  );
};

export default AbsenceView;
