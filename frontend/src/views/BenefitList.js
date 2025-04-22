import React, { useState, useEffect } from 'react';
import { getToken } from '../services/authService';
import BenefitForm from '../components/BenefitForm';

function BenefitList() {
    const [benefits, setBenefits] = useState([]);
    const [showForm, setShowForm] = useState(false);

    const loadBenefits = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/benefits', {
                headers: {
                    Authorization: `Bearer ${getToken()}`,
                },
            });

            if (!res.ok) throw new Error('Failed to fetch benefits');

            const data = await res.json();
            setBenefits(data);
        } catch (err) {
            console.error('Error loading benefits:', err);
        }
    };

    useEffect(() => {
        loadBenefits();
    }, []);

    return (
        <div>
            <h2>Benefits</h2>
            <button onClick={() => setShowForm(true)}>+ Add Benefit</button>

            {showForm && (
                <BenefitForm
                    onClose={() => setShowForm(false)}
                    onSubmitSuccess={loadBenefits}
                />
            )}

            <ul>
                {benefits.map((benefit) => (
                    <li key={benefit.id}>
                        <strong>{benefit.name}</strong>: ${benefit.amount}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default BenefitList;
