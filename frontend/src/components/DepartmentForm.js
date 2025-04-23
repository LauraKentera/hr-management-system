// src/components/DepartmentForm.js
import React from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, MenuItem, Button
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import departmentService from '../services/departmentService';

const DepartmentForm = ({ open, onClose, onSave, initialData = {}, managers = [] }) => {
  const isEdit = Boolean(initialData?.id);

  const validationSchema = Yup.object({
    name: Yup.string().required('Required'),
    managerId: Yup.number().nullable(),
  });

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      if (isEdit) {
        await departmentService.updateDepartment(initialData.id, values);
      } else {
        await departmentService.createDepartment(values);
      }
      onSave();
    } catch (error) {
      console.error('Department save error:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth>
      <DialogTitle>{isEdit ? 'Edit Department' : 'Add Department'}</DialogTitle>
      <Formik
        initialValues={{
          name: initialData.name || '',
          managerId: initialData.manager?.id || '',
        }}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize
      >
        {({ values, handleChange, errors, touched, isSubmitting }) => (
          <Form>
            <DialogContent>
              <TextField
                label="Department Name"
                name="name"
                value={values.name}
                onChange={handleChange}
                error={touched.name && Boolean(errors.name)}
                helperText={touched.name && errors.name}
                fullWidth
                margin="normal"
              />
              <TextField
                select
                label="Manager"
                name="managerId"
                value={values.managerId}
                onChange={handleChange}
                fullWidth
                margin="normal"
              >
                <MenuItem value="">None</MenuItem>
                {managers.map(manager => (
                  <MenuItem key={manager.id} value={manager.id}>
                    {manager.firstName} {manager.lastName}
                  </MenuItem>
                ))}
              </TextField>
            </DialogContent>
            <DialogActions>
              <Button onClick={onClose}>Cancel</Button>
              <Button type="submit" disabled={isSubmitting}>
                {isEdit ? 'Update' : 'Create'}
              </Button>
            </DialogActions>
          </Form>
        )}
      </Formik>
    </Dialog>
  );
};

export default DepartmentForm;
