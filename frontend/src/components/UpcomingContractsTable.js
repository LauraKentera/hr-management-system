import React, { useState, useEffect } from 'react';
import { Table, TableBody, TableCell, TableHead, TableRow, Paper } from '@mui/material';

const UpcomingContractsTable = () => {
    const [contracts, setContracts] = useState([
        { employeeName: 'John Doe', position: 'Engineer', contractEndDate: '2025-06-01' },
        { employeeName: 'Jane Smith', position: 'Sales Manager', contractEndDate: '2025-07-15' },
    ]);

    useEffect(() => {
        // Simulating fetching contract expiration data
        setContracts([
            { employeeName: 'John Doe', position: 'Engineer', contractEndDate: '2025-06-01' },
            { employeeName: 'Jane Smith', position: 'Sales Manager', contractEndDate: '2025-07-15' },
        ]);
    }, []);

    return (
        <Paper>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Employee Name</TableCell>
                        <TableCell>Position</TableCell>
                        <TableCell>Contract End Date</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {contracts.map((contract, index) => (
                        <TableRow key={index}>
                            <TableCell>{contract.employeeName}</TableCell>
                            <TableCell>{contract.position}</TableCell>
                            <TableCell>{contract.contractEndDate}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
};

export default UpcomingContractsTable;
