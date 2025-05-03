import React, { useState } from 'react';
import ContractList from './ContractList';
import ContractFormModal from '../components/ContractForm';
import ContractAnnexes from '../components/ContractAnnexes';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header (Topbar) component
import { Button, Box, Typography } from '@mui/material';
import { getRole } from '../utils/auth';

const ContractsView = () => {
    const [selected, setSelected] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [refreshKey, setRefreshKey] = useState(0);

    const role = getRole();  // Ensure role-based access is handled

    const onFormSuccess = () => setRefreshKey(k => k + 1);

    return (
        <div className="dashboard-page">
            {/* Sidebar and Topbar */}
            <Sidebar />
            <Header />

            {/* Main Content Area */}
            <div className="dashboard-container" style={{ padding: '2rem' }}>
                {/* Header Section */}
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Typography variant="h4" sx={{ fontWeight: 600, color: '#004e92' }}>
                        Contracts
                    </Typography>
                    <Box>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={() => { setSelected(null); setShowForm(true); }}
                            sx={{
                                textTransform: 'none',
                                backgroundColor: '#0077b6',
                                '&:hover': { backgroundColor: '#005f8a' },
                                marginRight: 2
                            }}
                        >
                            New Contract
                        </Button>
                        {role === 'Admin' && selected && (
                            <Button
                                variant="outlined"
                                color="primary"
                                onClick={() => setShowForm(true)}
                                disabled={!selected}
                                sx={{
                                    textTransform: 'none',
                                    borderColor: '#0077b6',
                                    color: '#0077b6',
                                    '&:hover': { borderColor: '#005f8a', color: '#005f8a' }
                                }}
                            >
                                Edit Contract
                            </Button>
                        )}
                    </Box>
                </Box>

                {/* Contract List Section */}
                <Box mb={3}>
                    <ContractList key={refreshKey} onSelect={setSelected} />
                </Box>

                {/* Show Contract Annexes if a contract is selected */}
                {selected && (
                    <Box mb={3}>
                        <ContractAnnexes contractId={selected.id} />
                    </Box>
                )}

                {/* Contract Form Modal */}
                <ContractFormModal
                    show={showForm}
                    onClose={() => setShowForm(false)}
                    onSuccess={onFormSuccess}
                    initialData={selected}
                />
            </div>
        </div>
    );
};

export default ContractsView;
