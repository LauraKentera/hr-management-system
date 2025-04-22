import React, { useState, useEffect } from 'react';
import { getToken } from '../services/authService';

function PayrollForm({ onClose, onSubmitSuccess }) {
    const [employees, setEmployees] = useState([]);
    const [form, setForm] = useState({
        employeeId: '',
        baseSalary: '',
        benefits: '',
        deductions: '',
        status: 'Pending',
    });

    useEffect(() => {
        // Load employees for the dropdown
        fetch('http://localhost:8080/api/employees', {
            headers: { Authorization: `Bearer ${getToken()}` },
        })
            .then((res) => res.json())
            .then((data) => setEmployees(data))
            .catch((err) => console.error('Failed to load employees:', err));
    }, []);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch('http://localhost:8080/api/payrolls', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${getToken()}`,
                },
                body: JSON.stringify(form),
            });

            if (!res.ok) throw new Error('Payroll creation failed');

            onSubmitSuccess(); // Notify parent to refresh
            onClose(); // Close modal
        } catch (err) {
            console.error(err);
            alert('Failed to create payroll');
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h3>Add Payroll Entry</h3>
                <form onSubmit={handleSubmit}>
                    <label>Employee</label>
                    <select name="employeeId" value={form.employeeId} onChange={handleChange} required>
                        <option value="">Select</option>
                        {employees.map(emp => (
                            <option key={emp.id} value={emp.id}>{emp.name}</option>
                        ))}
                    </select>

                    <label>Base Salary</label>
                    <input name="baseSalary" type="number" value={form.baseSalary} onChange={handleChange} required />

                    <label>Benefits</label>
                    <input name="benefits" type="number" value={form.benefits} onChange={handleChange} />

                    <label>Deductions</label>
                    <input name="deductions" type="number" value={form.deductions} onChange={handleChange} />

                    <label>Status</label>
                    <select name="status" value={form.status} onChange={handleChange}>
                        <option value="Pending">Pending</option>
                        <option value="Paid">Paid</option>
                    </select>

                    <button type="submit">Create</button>
                    <button type="button" onClick={onClose}>Cancel</button>
                </form>
            </div>
        </div>
    );
}

export default PayrollForm;
