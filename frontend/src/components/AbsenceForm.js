import React from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button, MenuItem
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import absenceService from '../services/absenceService';

const AbsenceForm = ({ open, onClose, onSave, initialData = {}, employees = [], types = [] }) => {
  const isEdit = Boolean(initialData?.id);

  const validationSchema = Yup.object({
    employeeId: Yup.number().required('Required'),
    type: Yup.string().required('Required'),
    date: Yup.string().required('Required'),
    status: Yup.string().required('Required'),
  });

  return (
    <Dialog open={open} onClose={onClose} fullWidth>
      <DialogTitle>{isEdit ? 'Edit Absence' : 'Add Absence'}</DialogTitle>
      <Formik
        initialValues={{
          employeeId: initialData?.employee?.id || '',
          type: initialData?.type || '',
          date: initialData?.date || '',
          status: initialData?.status || 'PENDING',
        }}
        
        validationSchema={validationSchema}
        onSubmit={async (values, { setSubmitting }) => {
          try {
            if (isEdit) {
              await absenceService.updateAbsence(initialData.id, values);
            } else {
              await absenceService.createAbsence(values);
            }
            onSave();
            onClose();
          } catch (err) {
            console.error('Error saving absence', err);
          } finally {
            setSubmitting(false);
          }
        }}
        enableReinitialize
      >
        {({ values, handleChange, touched, errors, isSubmitting }) => (
          <Form>
            <DialogContent>
              <TextField
                select label="Employee" name="employeeId" value={values.employeeId}
                onChange={handleChange} fullWidth margin="dense"
              >
                {employees.map(e => (
                  <MenuItem key={e.id} value={e.id}>
                    {e.firstName} {e.lastName}
                  </MenuItem>
                ))}
              </TextField>
              <TextField
                select label="Type" name="type" value={values.type}
                onChange={handleChange} fullWidth margin="dense"
              >
                {types.map(type => <MenuItem key={type} value={type}>{type}</MenuItem>)}
              </TextField>
              <TextField
                type="date" label="Date" name="date"
                value={values.date} onChange={handleChange}
                fullWidth margin="dense" InputLabelProps={{ shrink: true }}
              />
              <TextField
                select label="Status" name="status" value={values.status}
                onChange={handleChange} fullWidth margin="dense"
              >
                <MenuItem value="PENDING">Pending</MenuItem>
                <MenuItem value="APPROVED">Approved</MenuItem>
                <MenuItem value="REJECTED">Rejected</MenuItem>
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

export default AbsenceForm;
