import React, { useEffect, useState } from 'react';
import {
  Paper, Typography, Table, TableHead, TableBody, TableRow, TableCell,
  IconButton, Button, Dialog, DialogTitle, DialogContent, TextField, DialogActions
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';

const RefTableEditor = ({
  title,
  fetchList,
  createItem,
  updateItem,
  deleteItem,
  itemLabel = 'Name'
}) => {
  const [items, setItems] = useState([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(null);

  const load = async () => {
    const res = await fetchList();
    setItems(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const handleSave = async (values, { setSubmitting }) => {
    try {
      if (editing) {
        await updateItem(editing.id, values);
      } else {
        await createItem(values);
      }
      await load();
      setOpen(false);
    } finally {
      setSubmitting(false);
    }
  };

  const validationSchema = Yup.object({
    name: Yup.string().required('Required'),
  });

  return (
    <Paper sx={{ p: 2, mb: 4 }}>
      <Typography variant="h6" gutterBottom>{title}</Typography>
      <Button variant="outlined" onClick={() => { setEditing(null); setOpen(true); }} sx={{ mb: 1 }}>
        Add {itemLabel}
      </Button>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>{itemLabel}</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {items.map(i => (
            <TableRow key={i.id}>
              <TableCell>{i.name}</TableCell>
              <TableCell align="right">
                <IconButton size="small" onClick={() => { setEditing(i); setOpen(true); }}>
                  <EditIcon fontSize="small" />
                </IconButton>
                <IconButton size="small" onClick={() => deleteItem(i.id).then(load)}>
                  <DeleteIcon fontSize="small" />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)} fullWidth>
        <DialogTitle>{editing ? `Edit ${itemLabel}` : `Add ${itemLabel}`}</DialogTitle>
        <Formik
          initialValues={{ name: editing?.name || '' }}
          validationSchema={validationSchema}
          onSubmit={handleSave}
          enableReinitialize
        >
          {({ values, handleChange, errors, touched, isSubmitting }) => (
            <Form>
              <DialogContent>
                <TextField
                  label={itemLabel}
                  name="name"
                  value={values.name}
                  onChange={handleChange}
                  error={touched.name && Boolean(errors.name)}
                  helperText={touched.name && errors.name}
                  fullWidth
                  margin="dense"
                />
              </DialogContent>
              <DialogActions>
                <Button onClick={() => setOpen(false)}>Cancel</Button>
                <Button type="submit" disabled={isSubmitting}>
                  {editing ? 'Update' : 'Create'}
                </Button>
              </DialogActions>
            </Form>
          )}
        </Formik>
      </Dialog>
    </Paper>
  );
};

export default RefTableEditor;
