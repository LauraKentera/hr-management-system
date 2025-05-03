import React, { useState, useEffect } from 'react';
import contractService from '../services/contractService';
import { Link } from 'react-router-dom';
import { Box, Button, Table, TableHead, TableRow, TableCell, TableBody, Typography } from '@mui/material';

const ContractListPage = () => {
    const [contracts, setContracts] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        contractService.getAllContracts()
            .then(data => setContracts(data))
            .catch(err => setError(err.message));
    }, []);

    const handleDelete = (id) => {
        contractService.deleteContract(id)
            .then(() => {
                setContracts(contracts.filter(contract => contract.contractId !== id));
            })
            .catch(err => setError(err.message));
    };

    return (
        <Box p={3}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                <Typography variant="h4" sx={{ fontWeight: 600, color: '#004e92' }}>
                    Contract List
                </Typography>
                <Link to="/contracts/create" style={{ textDecoration: 'none' }}>
                    <Button
                        variant="contained"
                        color="primary"
                        sx={{
                            textTransform: 'none',
                            backgroundColor: '#0077b6',
                            '&:hover': { backgroundColor: '#005f8a' },
                        }}
                    >
                        Create New Contract
                    </Button>
                </Link>
            </Box>

            {error && (
                <Typography color="error" sx={{ mb: 2 }}>
                    {error}
                </Typography>
            )}

            <Box sx={{ overflowX: 'auto' }}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Employee Name</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Position</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Salary</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Contract Type</TableCell>
                            <TableCell sx={{ fontWeight: 600, color: '#004e92' }}>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {contracts.map((contract) => (
                            <TableRow key={contract.contractId}>
                                <TableCell>{contract.employeeName}</TableCell>
                                <TableCell>{contract.positionName}</TableCell>
                                <TableCell>{contract.salary}</TableCell>
                                <TableCell>{contract.contractType}</TableCell>
                                <TableCell>
                                    <Link to={`/contracts/edit/${contract.contractId}`} style={{ textDecoration: 'none' }}>
                                        <Button
                                            variant="outlined"
                                            size="small"
                                            sx={{
                                                mr: 1,
                                                borderColor: '#0077b6',
                                                color: '#0077b6',
                                                '&:hover': { borderColor: '#005f8a', color: '#005f8a' }
                                            }}
                                        >
                                            Edit
                                        </Button>
                                    </Link>
                                    <Button
                                        variant="outlined"
                                        color="error"
                                        size="small"
                                        onClick={() => handleDelete(contract.contractId)}
                                        sx={{
                                            borderColor: '#d32f2f',
                                            color: '#d32f2f',
                                            '&:hover': { borderColor: '#b71c1c', color: '#b71c1c' }
                                        }}
                                    >
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Box>
        </Box>
    );
};

export default ContractListPage;
