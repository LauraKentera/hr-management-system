import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
    createBenefit,
    fetchBenefitById,
    updateBenefit
} from '../services/benefitApi';
import { useAuth } from '../context/AuthContext';

function BenefitForm() {
    const { id } = useParams(); // undefined if creating
    const isEdit = Boolean(id);

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        taxable: false,
        active: true,
    });
    const [loading, setLoading] = useState(isEdit);
    const [error, setError] = useState(null);

    const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (isEdit) {
            fetchBenefitById(id)
                .then(data => {
                    setFormData({
                        name: data.name,
                        description: data.description,
                        taxable: data.taxable,
                        active: data.active
                    });
                    setLoading(false);
                })
                .catch(err => {
                    console.error(err);
                    setError('Failed to load benefit');
                    setLoading(false);
                });
        }
    }, [id, isEdit]);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (isEdit) {
                await updateBenefit(id, formData, user.id);
            } else {
                await createBenefit(formData, user.id);
            }
            navigate('/benefits');
        } catch (err) {
            console.error(err);
            setError('Failed to save benefit');
        }
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div className="container mt-4">
            <h2>{isEdit ? 'Edit' : 'Add'} Benefit</h2>

            {error && <p className="text-danger">{error}</p>}

            <form onSubmit={handleSubmit} className="mt-3">
                <div className="mb-3">
                    <label className="form-label">Name</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        className="form-control"
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Description</label>
                    <textarea
                        name="description"
                        value={formData.description}
                        className="form-control"
                        onChange={handleChange}
                    />
                </div>

                <div className="form-check mb-2">
                    <input
                        type="checkbox"
                        name="taxable"
                        checked={formData.taxable}
                        className="form-check-input"
                        onChange={handleChange}
                    />
                    <label className="form-check-label">Taxable</label>
                </div>

                <div className="form-check mb-4">
                    <input
                        type="checkbox"
                        name="active"
                        checked={formData.active}
                        className="form-check-input"
                        onChange={handleChange}
                    />
                    <label className="form-check-label">Active</label>
                </div>

                <button type="submit" className="btn btn-success me-2">
                    {isEdit ? 'Update' : 'Create'}
                </button>
                <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={() => navigate('/benefits')}
                >
                    Cancel
                </button>
            </form>
        </div>
    );
}

export default BenefitForm;
