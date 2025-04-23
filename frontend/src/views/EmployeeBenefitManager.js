import React, { useState, useEffect } from 'react';
import { getToken } from '../services/authService';

function EmployeeBenefitManager() {
    const [employees, setEmployees] = useState([]);
    const [benefits, setBenefits] = useState([]);
    const [selectedEmployeeId, setSelectedEmployeeId] = useState('');
    const [assignedBenefitIds, setAssignedBenefitIds] = useState([]);

    useEffect(() => {
        fetchData();
    }, []);

    useEffect(() => {
        if (selectedEmployeeId) {
            loadEmployeeBenefits(selectedEmployeeId);
        } else {
            setAssignedBenefitIds([]);
        }
    }, [selectedEmployeeId]);

    const fetchData = async () => {
        const token = getToken();
        const headers = { Authorization: `Bearer ${token}` };

        const [empRes, benRes] = await Promise.all([
            fetch('http://localhost:8080/api/employees', { headers }),
            fetch('http://localhost:8080/api/benefits', { headers }),
        ]);

        const [empData, benData] = await Promise.all([empRes.json(), benRes.json()]);
        setEmployees(empData);
        setBenefits(benData);
    };

    const loadEmployeeBenefits = async (empId) => {
        try {
            const res = await fetch(`http://localhost:8080/api/employees/${empId}/benefits`, {
                headers: { Authorization: `Bearer ${getToken()}` },
            });
            const data = await res.json();
            setAssignedBenefitIds(data.map((b) => b.id));
        } catch (err) {
            console.error('Error loading assigned benefits:', err);
        }
    };

    const toggleBenefit = (benefitId) => {
        setAssignedBenefitIds((prev) =>
            prev.includes(benefitId)
                ? prev.filter((id) => id !== benefitId)
                : [...prev, benefitId]
        );
    };

    const saveAssignments = async () => {
        try {
            const res = await fetch(`http://localhost:8080/api/employees/${selectedEmployeeId}/benefits`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${getToken()}`,
                },
                body: JSON.stringify(assignedBenefitIds),
            });

            if (!res.ok) throw new Error('Failed to assign benefits');

            alert('Benefits updated!');
        } catch (err) {
            console.error('Failed to update benefits:', err);
            alert('Error assigning benefits');
        }
    };

    return (
        <div>
            <h2>Assign Benefits to Employees</h2>

            <label>Select Employee:</label>
            <select value={selectedEmployeeId} onChange={(e) => setSelectedEmployeeId(e.target.value)}>
                <option value="">-- Select --</option>
                {employees.map((emp) => (
                    <option key={emp.id} value={emp.id}>
                        {emp.name}
                    </option>
                ))}
            </select>

            {selectedEmployeeId && (
                <div>
                    <h4>Assign Benefits:</h4>
                    {benefits.map((ben) => (
                        <div key={ben.id}>
                            <label>
                                <input
                                    type="checkbox"
                                    checked={assignedBenefitIds.includes(ben.id)}
                                    onChange={() => toggleBenefit(ben.id)}
                                />
                                {ben.name} (${ben.amount})
                            </label>
                        </div>
                    ))}

                    <button onClick={saveAssignments}>Save Changes</button>
                </div>
            )}
        </div>
    );
}

export default EmployeeBenefitManager;
