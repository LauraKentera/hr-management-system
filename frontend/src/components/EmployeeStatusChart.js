import React, { useState, useEffect } from 'react';
import { PieChart, Pie, Cell, Tooltip, Legend } from 'recharts';

const EmployeeStatusChart = () => {
    const [employeeStats, setEmployeeStats] = useState({
        total: 100,
        active: 85,
        onLeave: 10,
        terminated: 5,
    });

    useEffect(() => {
        // Simulating fetching data from the backend
        setEmployeeStats({
            total: 100,
            active: 85,
            onLeave: 10,
            terminated: 5,
        });
    }, []);

    const data = [
        { name: 'Active', value: employeeStats.active },
        { name: 'On Leave', value: employeeStats.onLeave },
        { name: 'Terminated', value: employeeStats.terminated },
    ];

    return (
        <PieChart width={400} height={400}>
            <Pie data={data} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={150}>
                <Cell fill="#8884d8" />
                <Cell fill="#ff7300" />
                <Cell fill="#82ca9d" />
            </Pie>
            <Tooltip />
            <Legend />
        </PieChart>
    );
};

export default EmployeeStatusChart;
