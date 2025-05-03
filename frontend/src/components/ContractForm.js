// components/ContractForm.js
import React, { useState } from 'react';

const ContractForm = ({ formData, onSubmit }) => {
  const [formValues, setFormValues] = useState(formData);

  const handleChange = (e) => {
    setFormValues({
      ...formValues,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formValues);  // Trigger the onSubmit function passed as prop
  };

  return (
      <form onSubmit={handleSubmit}>
        {/* Form fields for creating/editing a contract */}
        <label>Employee ID</label>
        <input
            type="number"
            name="employeeId"
            value={formValues.employeeId}
            onChange={handleChange}
            required
        />
        <label>Position ID</label>
        <input
            type="number"
            name="positionId"
            value={formValues.positionId}
            onChange={handleChange}
            required
        />
        <label>Start Date</label>
        <input
            type="date"
            name="startDate"
            value={formValues.startDate}
            onChange={handleChange}
            required
        />
        {/* Add other form fields similarly */}
        <button type="submit">{formData ? 'Update Contract' : 'Create Contract'}</button>
      </form>
  );
};

export default ContractForm;
