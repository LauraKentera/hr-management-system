import React, { useState, useEffect } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const SalaryByDepartmentChart = () => {
    const [departmentSalaryData, setDepartmentSalaryData] = useState([
        { name: 'HR', avgSalary: 50000 },
        { name: 'Engineering', avgSalary: 70000 },
        { name: 'Sales', avgSalary: 60000 },
        { name: 'Marketing', avgSalary: 55000 },
    ]);

    useEffect(() => {
        // Simulating fetching department salary data
        setDepartmentSalaryData([
            { name: 'HR', avgSalary: 50000 },
            { name: 'Engineering', avgSalary: 70000 },
            { name: 'Sales', avgSalary: 60000 },
            { name: 'Marketing', avgSalary: 55000 },
        ]);
    }, []);

    return (
        <BarChart width={500} height={300} data={departmentSalaryData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="avgSalary" fill="#8884d8" />
        </BarChart>
    );
};

export default SalaryByDepartmentChart;
