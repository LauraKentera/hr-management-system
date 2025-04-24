import React, { useState } from 'react';

function BenefitForm({ onClose, onSubmitSuccess }) {
    const [form, setForm] = useState({
        name: '',
        amount: '',
    });

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Simulate adding the benefit by logging the form data
        console.log('Benefit added:', form);

        // Simulate success
        onSubmitSuccess(); // Call to reload the list
        onClose(); // Close the form after submission
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <h3>Add Benefit</h3>
                <form onSubmit={handleSubmit}>
                    <label>Name</label>
                    <input
                        name="name"
                        value={form.name}
                        onChange={handleChange}
                        required
                    />

                    <label>Amount</label>
                    <input
                        name="amount"
                        type="number"
                        value={form.amount}
                        onChange={handleChange}
                        required
                    />

                    <button type="submit">Save</button>
                    <button type="button" onClick={onClose}>Cancel</button>
                </form>
            </div>
        </div>
    );
}

export default BenefitForm;
