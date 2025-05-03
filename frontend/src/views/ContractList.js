import React, { useState, useEffect } from 'react';
import contractService from '../services/contractService';
import { Link } from 'react-router-dom';
import '../styles/ContractsList.css';  // Correct the path if needed

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
        <div className="contract-list-container">
            <h1>Contract List</h1>
            {error && <p className="error-message">{error}</p>}
            <Link to="/contracts/create" className="create-btn">Create New Contract</Link>
            <div className="table-container">
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
                                <Link to={`/contracts/edit/${contract.contractId}`} className="edit-btn">Edit</Link>
                                <button className="delete-btn" onClick={() => handleDelete(contract.contractId)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ContractListPage;
