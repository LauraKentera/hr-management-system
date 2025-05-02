import React from 'react';
import { Container, Typography, Snackbar, CircularProgress } from '@mui/material';
import RefTableEditor from '../components/RefTableEditor';
import {
  getNationalities, createNationality, updateNationality, deleteNationality,
  getEducationLevels, createEducationLevel, updateEducationLevel, deleteEducationLevel
} from '../services/settingsService';

const SettingsView = () => {
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState(null);

  const handleError = (err) => {
    setError(err?.response?.data?.message || err.message || "An error occurred.");
  };

  return (
    <Container sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>Settings</Typography>

      {/* Loading Spinner */}
      {loading && <CircularProgress />}

      {/* Error Snackbar */}
      <Snackbar
        open={Boolean(error)}
        message={error}
        autoHideDuration={6000}
        onClose={() => setError(null)}
      />

      {/* Nationality RefTableEditor */}
      <RefTableEditor
        title="Nationality"
        fetchList={getNationalities}
        createItem={createNationality}
        updateItem={updateNationality}
        deleteItem={deleteNationality}
        itemLabel="Nationality"
        fieldName="country"           // UI input field
        submitFieldName="name"        // backend expects { country: "..." }
        onError={handleError}
        setLoading={setLoading}
      />

    </Container>
  );
};

export default SettingsView;
