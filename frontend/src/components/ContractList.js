import React, { useState, useEffect } from 'react';
import { getContracts } from '../services/contractService';

const ContractList = ({ onSelect }) => {
  const [contracts, setContracts] = useState([]);
  const [employeeFilter, setEmployeeFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('all');

  useEffect(() => {
    (async () => {
      const { data } = await getContracts({
        employee: employeeFilter,
        status: statusFilter,
      });
      setContracts(data);
    })();
  }, [employeeFilter, statusFilter]);

  return (
    <div>
      <h3>Contracts</h3>
      <div className="filters">
        <input
          placeholder="Filter by employee"
          value={employeeFilter}
          onChange={e => setEmployeeFilter(e.target.value)}
        />
        <select
          value={statusFilter}
          onChange={e => setStatusFilter(e.target.value)}
        >
          <option value="all">All</option>
          <option value="active">Active</option>
          <option value="expired">Expired</option>
        </select>
      </div>
      <table>
        <thead>
          <tr>
            <th>ID</th><th>Employee</th><th>Position</th><th>Start</th><th>End</th>
          </tr>
        </thead>
        <tbody>
          {contracts.map(c => (
            <tr key={c.id} onClick={() => onSelect(c)}>
              <td>{c.id}</td>
              <td>{c.employeeName}</td>
              <td>{c.positionCode}</td>
              <td>{c.startDate}</td>
              <td>{c.endDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ContractList;
