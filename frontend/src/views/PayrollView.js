import React, { useState, useEffect } from 'react';
import { getToken } from '../services/authService';
import PayrollForm from '../components/PayrollForm';

function PayrollView() {
    const [entries, setEntries] = useState([]);
    const [filterStatus, setFilterStatus] = useState('All');
    const [showForm, setShowForm] = useState(false);

    const loadPayrolls = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/payrolls', {
                headers: {
                    Authorization: `Bearer ${getToken()}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!res.ok) {
                throw new Error('Failed to fetch payrolls');
            }

            const data = await res.json();
            setEntries(data);
        } catch (err) {
            console.error('Error loading payrolls:', err);
        }
    };

    useEffect(() => {
        loadPayrolls();
    }, []);

    const filtered = entries.filter(
        (entry) => filterStatus === 'All' || entry.status === filterStatus
    );

    const calcNetPay = (entry) =>
        entry.baseSalary + (entry.benefits || 0) - (entry.deductions || 0);

    return (
        <div>
            <h2>Payroll</h2>

            <div style={{ marginBottom: '1rem' }}>
                <button onClick={() => setShowForm(true)}>+ Add Payroll Entry</button>
                <label style={{ marginLeft: '1rem' }}>Status Filter: </label>
                <select value={filterStatus} onChange={(e) => setFilterStatus(e.target.value)}>
                    <option value="All">All</option>
                    <option value="Paid">Paid</option>
                    <option value="Pending">Pending</option>
                </select>
            </div>

            {showForm && (
                <PayrollForm
                    onClose={() => setShowForm(false)}
                    onSubmitSuccess={loadPayrolls}
                />
            )}

            <table>
                <thead>
                    <tr>
                        <th>Employee</th>
                        <th>Base Salary</th>
                        <th>Benefits</th>
                        <th>Deductions</th>
                        <th>Net Pay</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {filtered.map((entry) => (
                        <tr key={entry.id}>
                            <td>{entry.employee?.name || 'N/A'}</td>
                            <td>${entry.baseSalary}</td>
                            <td>${entry.benefits || 0}</td>
                            <td>${entry.deductions || 0}</td>
                            <td>${calcNetPay(entry)}</td>
                            <td>{entry.status}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default PayrollView;
