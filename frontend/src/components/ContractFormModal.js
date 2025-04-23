import React, { useState, useEffect } from 'react';
import { createContract, updateContract } from '../services/contractService';

const ContractFormModal = ({ show, onClose, onSuccess, initialData }) => {
  const [employeeId, setEmployeeId] = useState('');
  const [positionId, setPositionId] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  useEffect(() => {
    if (initialData) {
      setEmployeeId(initialData.employeeId);
      setPositionId(initialData.positionId);
      setStartDate(initialData.startDate);
      setEndDate(initialData.endDate);
    } else {
      setEmployeeId(''); setPositionId(''); setStartDate(''); setEndDate('');
    }
  }, [initialData]);

  if (!show) return null;

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      if (initialData?.id) {
        await updateContract(initialData.id, { employeeId, positionId, startDate, endDate });
      } else {
        await createContract({ employeeId, positionId, startDate, endDate });
      }
      onSuccess();
      onClose();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="modal-backdrop">
      <div className="modal">
        <h3>{initialData ? 'Edit' : 'New'} Contract</h3>
        <form onSubmit={handleSubmit}>
          <label>Employee ID</label>
          <input type="number" value={employeeId} onChange={e => setEmployeeId(e.target.value)} required />
          <label>Position ID</label>
          <input type="number" value={positionId} onChange={e => setPositionId(e.target.value)} required />
          <label>Start Date</label>
          <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} required />
          <label>End Date</label>
          <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} required />
          <button type="submit">Save</button>
          <button type="button" onClick={onClose}>Cancel</button>
        </form>
      </div>
    </div>
  );
};

export default ContractFormModal;
