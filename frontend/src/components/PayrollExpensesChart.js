import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';

const PayrollExpensesChart = () => {
    const [payrollData, setPayrollData] = useState([
        { month: 'Jan', totalExpenses: 500000 },
        { month: 'Feb', totalExpenses: 510000 },
        { month: 'Mar', totalExpenses: 520000 },
        { month: 'Apr', totalExpenses: 530000 },
        { month: 'May', totalExpenses: 540000 },
    ]);

    useEffect(() => {
        // Simulating fetching payroll data
        setPayrollData([
            { month: 'Jan', totalExpenses: 500000 },
            { month: 'Feb', totalExpenses: 510000 },
            { month: 'Mar', totalExpenses: 520000 },
            { month: 'Apr', totalExpenses: 530000 },
            { month: 'May', totalExpenses: 540000 },
        ]);
    }, []);

    return (
        <LineChart width={500} height={300} data={payrollData}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="totalExpenses" stroke="#8884d8" />
        </LineChart>
    );
};

export default PayrollExpensesChart;
