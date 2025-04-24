import React, { useState } from 'react';
import ContractList from '../components/ContractList';
import ContractFormModal from '../components/ContractFormModal';
import ContractAnnexes from '../components/ContractAnnexes';
import Sidebar from '../components/Sidebar';  // Import Sidebar component
import Header from '../components/Topbar';  // Import Header (Topbar) component
import '../styles/Dashboard.css';  // Ensure Dashboard CSS is applied
import { Button, Box, Typography } from '@mui/material';
import '../styles/Contracts.css';

const ContractsView = () => {
    const [selected, setSelected] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [refreshKey, setRefreshKey] = useState(0);

    const onFormSuccess = () => setRefreshKey(k => k + 1);

    return (
        <div className="dashboard-page">
            {/* Sidebar and Topbar */}
            <Sidebar />
            <Header />

            {/* Main Content Area */}
            <div className="dashboard-container">
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Typography variant="h4">Contracts</Typography>
                    <Box>
                        <Button variant="contained" color="primary" onClick={() => { setSelected(null); setShowForm(true); }} sx={{ mr: 2 }}>
                            New Contract
                        </Button>
                        <Button
                            variant="outlined"
                            color="primary"
                            onClick={() => selected && setShowForm(true)}
                            disabled={!selected}
                        >
                            Edit Contract
                        </Button>
                    </Box>
                </Box>

                {/* Contract List */}
                <ContractList key={refreshKey} onSelect={setSelected} />

                {/* Show Contract Annexes if a contract is selected */}
                {selected && <ContractAnnexes contractId={selected.id} />}

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
