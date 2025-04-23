import React, { useEffect, useState } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const AttendanceGraph = () => {
    const [data, setData] = useState([
        { name: 'Mon', attendance: 85, sickLeave: 20 },
        { name: 'Tue', attendance: 90, sickLeave: 10 },
        { name: 'Wed', attendance: 87, sickLeave: 12 },
        { name: 'Thu', attendance: 92, sickLeave: 5 },
        { name: 'Fri', attendance: 95, sickLeave: 3 },
    ]);

    return (
        <ResponsiveContainer width="100%" height={300}>
            <LineChart data={data}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip className="custom-tooltip" />
                <Legend className="custom-legend" />

                {/* Adding lines with classes to control colors in CSS */}
                <Line type="monotone" dataKey="attendance" stroke="#8884d8" className="attendance-line" />
                <Line type="monotone" dataKey="sickLeave" stroke="#82ca9d" className="sickLeave-line" />
            </LineChart>
        </ResponsiveContainer>
    );
};

export default AttendanceGraph;
