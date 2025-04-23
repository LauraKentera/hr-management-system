// src/components/EmployeeForm.js
import React, { useEffect } from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button, MenuItem, Grid
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import employeeService from '../services/employeeService';

const EmployeeForm = ({ open, onClose, onSave, initialData = {}, departments = [] }) => {
  const isEditMode = Boolean(initialData && initialData.id);

  const validationSchema = Yup.object({
    firstName: Yup.string().required('Required'),
    lastName: Yup.string().required('Required'),
    email: Yup.string().email('Invalid email').required('Required'),
    departmentId: Yup.number().required('Required'),
  });

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      if (isEditMode) {
        await employeeService.updateEmployee(initialData.id, values);
      } else {
        await employeeService.createEmployee(values);
      }
      onSave(); // Callback to refresh data
      onClose();
    } catch (error) {
      console.error('Save failed:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth>
      <DialogTitle>{isEditMode ? 'Edit Employee' : 'Add New Employee'}</DialogTitle>
      <Formik
        initialValues={{
          firstName: initialData.firstName || '',
          lastName: initialData.lastName || '',
          email: initialData.email || '',
          departmentId: initialData.department?.id || '',
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize
      >
        {({ values, handleChange, errors, touched, isSubmitting }) => (
          <Form>
            <DialogContent>
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <TextField
                    label="First Name"
                    name="firstName"
                    value={values.firstName}
                    onChange={handleChange}
                    error={touched.firstName && Boolean(errors.firstName)}
                    helperText={touched.firstName && errors.firstName}
                    fullWidth
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    label="Last Name"
                    name="lastName"
                    value={values.lastName}
                    onChange={handleChange}
                    error={touched.lastName && Boolean(errors.lastName)}
                    helperText={touched.lastName && errors.lastName}
                    fullWidth
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="Email"
                    name="email"
                    value={values.email}
                    onChange={handleChange}
                    error={touched.email && Boolean(errors.email)}
                    helperText={touched.email && errors.email}
                    fullWidth
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    select
                    label="Department"
                    name="departmentId"
                    value={values.departmentId}
                    onChange={handleChange}
                    error={touched.departmentId && Boolean(errors.departmentId)}
                    helperText={touched.departmentId && errors.departmentId}
                    fullWidth
                  >
                    {departments.map(dep => (
                      <MenuItem key={dep.id} value={dep.id}>
                        {dep.name}
                      </MenuItem>
                    ))}
                  </TextField>
                </Grid>
              </Grid>
            </DialogContent>
            <DialogActions>
              <Button onClick={onClose}>Cancel</Button>
              <Button type="submit" disabled={isSubmitting}>
                {isEditMode ? 'Update' : 'Create'}
              </Button>
            </DialogActions>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
};

export default EmployeeForm;
