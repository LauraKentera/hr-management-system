import React from 'react';
import { Container, Typography } from '@mui/material';
import RefTableEditor from '../components/RefTableEditor';
import {
  getNationalities, createNationality, updateNationality, deleteNationality,
  getEducationLevels, createEducationLevel, updateEducationLevel, deleteEducationLevel
} from '../services/settingsService';

const SettingsView = () => (
  <Container sx={{ py: 4 }}>
    <Typography variant="h4" gutterBottom>Settings</Typography>

    <RefTableEditor
      title="Nationality"
      fetchList={getNationalities}
      createItem={createNationality}
      updateItem={updateNationality}
      deleteItem={deleteNationality}
      itemLabel="Nationality"
    />

    <RefTableEditor
      title="Education Levels"
      fetchList={getEducationLevels}
      createItem={createEducationLevel}
      updateItem={updateEducationLevel}
      deleteItem={deleteEducationLevel}
      itemLabel="Education Level"
    />
  </Container>
);

export default SettingsView;
