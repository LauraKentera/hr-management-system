import React, { useState, useEffect } from 'react';
import { Card, CardContent, Typography, Button, Grid, Box } from '@mui/material';

const ContractList = ({ onSelect }) => {
    const [contracts, setContracts] = useState([]);
    const [employeeFilter, setEmployeeFilter] = useState('');
    const [statusFilter, setStatusFilter] = useState('all');

    // Dummy contract data
    useEffect(() => {
        const dummyContracts = [
            { id: 1, employeeName: 'John Doe', positionCode: 'E001', startDate: '2025-01-01', endDate: '2025-12-31', status: 'active' },
            { id: 2, employeeName: 'Jane Smith', positionCode: 'E002', startDate: '2025-02-01', endDate: '2025-11-30', status: 'expired' },
        ];
        setContracts(dummyContracts);
    }, []);

    return (
        <div>
            <Box sx={{ mb: 2 }}>
                <input
                    placeholder="Filter by employee"
                    value={employeeFilter}
                    onChange={e => setEmployeeFilter(e.target.value)}
                    style={{ padding: '8px', width: '200px' }}
                />
                <select
                    value={statusFilter}
                    onChange={e => setStatusFilter(e.target.value)}
                    style={{ padding: '8px', marginLeft: '10px' }}
                >
                    <option value="all">All</option>
                    <option value="active">Active</option>
                    <option value="expired">Expired</option>
                </select>
            </Box>

            {/* Grid container with proper spacing */}
            <Grid container spacing={3}>
                {contracts
                    .filter(c =>
                        (statusFilter === 'all' || c.status === statusFilter) &&
                        (c.employeeName.toLowerCase().includes(employeeFilter.toLowerCase()))
                    )
                    .map(c => (
                        <Grid item xs={12} sm={6} md={4} key={c.id}>
                            <Card className="contract-card" sx={{ boxShadow: 3, borderRadius: 2 }}>
                                <CardContent>
                                    <Typography variant="h6">{c.employeeName}</Typography>
                                    <Typography variant="body2" color="textSecondary">Position: {c.positionCode}</Typography>
                                    <Typography variant="body2" color="textSecondary">Start: {c.startDate}</Typography>
                                    <Typography variant="body2" color="textSecondary">End: {c.endDate}</Typography>
                                    <Typography variant="body2" color="textSecondary">Status: {c.status}</Typography>
                                </CardContent>
                                <Box sx={{ display: 'flex', justifyContent: 'flex-end', padding: '10px' }}>
                                    <Button size="small" color="primary" onClick={() => onSelect(c)}>
                                        Select
                                    </Button>
                                </Box>
                            </Card>
                        </Grid>
                    ))}
            </Grid>
        </div>
    );
};

export default ContractList;
