import React, { useState } from 'react';
import { getToken } from '../services/authService';

function BenefitForm({ onClose, onSubmitSuccess }) {
    const [form, setForm] = useState({
        name: '',
        amount: '',
    });

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch('http://localhost:8080/api/benefits', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${getToken()}`,
                },
                body: JSON.stringify(form),
            });

            if (!res.ok) throw new Error('Failed to add benefit');

            onSubmitSuccess();
            onClose();
        } catch (err) {
            console.error('Error adding benefit:', err);
            alert('Failed to add benefit');
        }
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h3>Add Benefit</h3>
                <form onSubmit={handleSubmit}>
                    <label>Name</label>
                    <input name="name" value={form.name} onChange={handleChange} required />

                    <label>Amount</label>
                    <input name="amount" type="number" value={form.amount} onChange={handleChange} required />

                    <button type="submit">Save</button>
                    <button type="button" onClick={onClose}>Cancel</button>
                </form>
            </div>
        </div>
    );
}

export default BenefitForm;
