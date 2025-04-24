import React, { useState, useEffect } from 'react';

function PayrollForm({ onClose, onSubmitSuccess }) {
    // Dummy employee data for testing
    const [employees, setEmployees] = useState([
        { id: 1, name: 'John Doe' },
        { id: 2, name: 'Jane Smith' },
        { id: 3, name: 'Michael Brown' },
        { id: 4, name: 'Emily Davis' },
    ]);

    const [form, setForm] = useState({
        employeeId: '',
        baseSalary: '',
        benefits: '',
        deductions: '',
        status: 'Pending',
    });

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Simulating the payroll data submission
        const payrollData = { ...form };

        console.log('Payroll Data:', payrollData);

        // Simulate success by notifying the parent component
        onSubmitSuccess();
        onClose();
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h3>Add Payroll Entry</h3>
                <form onSubmit={handleSubmit}>
                    <label>Employee</label>
                    <select
                        name="employeeId"
                        value={form.employeeId}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select</option>
                        {employees.map(emp => (
                            <option key={emp.id} value={emp.id}>
                                {emp.name}
                            </option>
                        ))}
                    </select>

                    <label>Base Salary</label>
                    <input
                        name="baseSalary"
                        type="number"
                        value={form.baseSalary}
                        onChange={handleChange}
                        required
                    />

                    <label>Benefits</label>
                    <input
                        name="benefits"
                        type="number"
                        value={form.benefits}
                        onChange={handleChange}
                    />

                    <label>Deductions</label>
                    <input
                        name="deductions"
                        type="number"
                        value={form.deductions}
                        onChange={handleChange}
                    />

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
