import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import contractService from '../services/contractService';
import '../styles/ContractsFormPage.css';  // Adjusted the path to the correct file

const ContractFormPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        employeeId: '',
        positionId: '',
        startDate: '',
        endDate: '',
        salary: '',
        contractType: '',
        signedDate: '',
        documentPath: ''
    });
    const [error, setError] = useState('');

    // Fetch contract data if we are editing an existing contract
    useEffect(() => {
        if (id) {
            contractService.getContractById(id)
                .then(data => setFormData(data))
                .catch(err => setError(err.message));
        }
    }, [id]);

    // Handle form input changes
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        if (id) {
            contractService.updateContract(id, formData)
                .then(() => navigate('/contracts'))
                .catch(err => setError(err.message));
        } else {
            contractService.createContract(formData)
                .then(() => navigate('/contracts'))
                .catch(err => setError(err.message));
        }
    };

    return (
        <div className="contract-form-container">
            <h1 className="form-heading">{id ? 'Edit Contract' : 'Create Contract'}</h1>
            {error && <p className="error-message">{error}</p>}

            <form onSubmit={handleSubmit} className="contract-form">
                <label htmlFor="employeeId">Employee ID</label>
                <input
                    id="employeeId"
                    type="number"
                    name="employeeId"
                    value={formData.employeeId}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <label htmlFor="positionId">Position ID</label>
                <input
                    id="positionId"
                    type="number"
                    name="positionId"
                    value={formData.positionId}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <label htmlFor="startDate">Start Date</label>
                <input
                    id="startDate"
                    type="date"
                    name="startDate"
                    value={formData.startDate}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <label htmlFor="endDate">End Date</label>
                <input
                    id="endDate"
                    type="date"
                    name="endDate"
                    value={formData.endDate}
                    onChange={handleChange}
                    className="form-input"
                />

                <label htmlFor="salary">Salary</label>
                <input
                    id="salary"
                    type="number"
                    name="salary"
                    value={formData.salary}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <label htmlFor="contractType">Contract Type</label>
                <input
                    id="contractType"
                    type="text"
                    name="contractType"
                    value={formData.contractType}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <label htmlFor="signedDate">Signed Date</label>
                <input
                    id="signedDate"
                    type="date"
                    name="signedDate"
                    value={formData.signedDate}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <label htmlFor="documentPath">Document Path</label>
                <input
                    id="documentPath"
                    type="text"
                    name="documentPath"
                    value={formData.documentPath}
                    onChange={handleChange}
                    required
                    className="form-input"
                />

                <button type="submit" className="submit-btn">{id ? 'Update Contract' : 'Create Contract'}</button>
            </form>
        </div>
    );
};

export default ContractFormPage;
