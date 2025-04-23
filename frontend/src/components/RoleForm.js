import React from 'react';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  TextField, Button
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import userService from '../services/userService';

const RoleForm = ({ open, onClose, onSave, initialData = {} }) => {
  const isEdit = Boolean(initialData.id);

  const validationSchema = Yup.object({
    roleName: Yup.string().required('Required'),
  });

  return (
    <Dialog open={open} onClose={onClose} fullWidth>
      <DialogTitle>{isEdit ? 'Edit Role' : 'Add Role'}</DialogTitle>
      <Formik
        initialValues={{ roleName: initialData.roleName || '' }}
        validationSchema={validationSchema}
        onSubmit={async (values, { setSubmitting }) => {
          try {
            if (isEdit) {
              await userService.updateRole(initialData.id, { roleName: values.roleName });
            } else {
              await userService.createRole({ roleName: values.roleName });
            }
            onSave();
            onClose();
          } catch (err) {
            console.error('Role save error:', err);
          } finally {
            setSubmitting(false);
          }
        }}
        enableReinitialize
      >
        {({ values, handleChange, errors, touched, isSubmitting }) => (
          <Form>
            <DialogContent>
              <TextField
                label="Role Name"
                name="roleName"
                value={values.roleName}
                onChange={handleChange}
                error={touched.roleName && Boolean(errors.roleName)}
                helperText={touched.roleName && errors.roleName}
                fullWidth
                margin="dense"
              />
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

export default RoleForm;
