import React, { useState, useEffect } from 'react';
import { PieChart, Pie, Cell, Tooltip, Legend } from 'recharts';

const AbsenceTypeChart = () => {
    const [absenceData, setAbsenceData] = useState({
        sickLeave: 50,
        personalLeave: 30,
        vacation: 20,
    });

    useEffect(() => {
        // Simulating fetching absence data
        setAbsenceData({
            sickLeave: 50,
            personalLeave: 30,
            vacation: 20,
        });
    }, []);

    const data = [
        { name: 'Sick Leave', value: absenceData.sickLeave },
        { name: 'Personal Leave', value: absenceData.personalLeave },
        { name: 'Vacation', value: absenceData.vacation },
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

export default AbsenceTypeChart;