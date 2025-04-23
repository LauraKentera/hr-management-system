import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const EmployeeGrowthChart = () => {
    const [growthData, setGrowthData] = useState([
        { month: 'Jan', totalEmployees: 100 },
        { month: 'Feb', totalEmployees: 120 },
        { month: 'Mar', totalEmployees: 140 },
        { month: 'Apr', totalEmployees: 160 },
        { month: 'May', totalEmployees: 180 },
    ]);

    useEffect(() => {
        // Simulating fetching employee growth data
        setGrowthData([
            { month: 'Jan', totalEmployees: 100 },
            { month: 'Feb', totalEmployees: 120 },
            { month: 'Mar', totalEmployees: 140 },
            { month: 'Apr', totalEmployees: 160 },
            { month: 'May', totalEmployees: 180 },
        ]);
    }, []);

    return (
        <LineChart width={500} height={300} data={growthData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="totalEmployees" stroke="#8884d8" />
        </LineChart>
    );
};

export default EmployeeGrowthChart;
