import React, { useState, useEffect } from 'react';
import { PieChart, Pie, Cell, Tooltip, Legend } from 'recharts';
import ApiEndpoints from '../api/ApiEndpoints'; // Import API endpoints
import axios from 'axios'; // Import axios for API calls

const AbsenceTypeChart = () => {
    const [absenceData, setAbsenceData] = useState(null); // Initialize with null to handle loading state

    useEffect(() => {
        // Fetch absence data from the API
        const fetchAbsenceData = async () => {
            try {
                const response = await axios.get(ApiEndpoints.absencses.getAll);
                const absences = response.data;

                // Transform the API response to match the chart data structure
                const transformedData = {
                    sickLeave: absences.sickLeave || 0,
                    personalLeave: absences.personalLeave || 0,
                    vacation: absences.vacation || 0,
                };

                setAbsenceData(transformedData);
            } catch (error) {
                console.error('Error fetching absence data:', error);
            }
        };

        fetchAbsenceData();
    }, []);

    // Handle loading state
    if (!absenceData) {
        return <p>Loading...</p>;
    }

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