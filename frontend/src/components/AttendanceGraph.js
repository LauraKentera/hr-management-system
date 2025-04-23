import React, { useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const AttendanceGraph = () => {
    const data = [
        { name: 'Mon', attendance: 85 },
        { name: 'Tue', attendance: 90 },
        { name: 'Wed', attendance: 87 },
        { name: 'Thu', attendance: 92 },
        { name: 'Fri', attendance: 95 },
    ];

    // We no longer need the useRef here

    return (
        <ResponsiveContainer width="100%" height={300}>
            <LineChart data={data}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="attendance" stroke="#8884d8" />
            </LineChart>
        </ResponsiveContainer>
    );
};

export default AttendanceGraph;
