// components/ContractTable.js
import React from 'react';
import { Link } from 'react-router-dom';

const ContractTable = ({ contracts }) => {
    return (
        <table>
            <thead>
            <tr>
                <th>Employee Name</th>
                <th>Position</th>
                <th>Salary</th>
                <th>Contract Type</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {contracts.map(contract => (
                <tr key={contract.contractId}>
                    <td>{contract.employeeName}</td>
                    <td>{contract.positionName}</td>
                    <td>{contract.salary}</td>
                    <td>{contract.contractType}</td>
                    <td>
                        <Link to={`/contracts/edit/${contract.contractId}`}>Edit</Link>
                        <button onClick={() => handleDelete(contract.contractId)}>Delete</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default ContractTable;
