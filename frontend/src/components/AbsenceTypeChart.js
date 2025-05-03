import React from 'react';
import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Paper, Typography } from '@mui/material';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884D8'];

const AbsenceTypeChart = ({ absences }) => {
    const processData = () => {
        if (!absences || absences.length === 0) return [];
        
        const typeCounts = absences.reduce((acc, absence) => {
            const type = absence.type || 'Unknown';
            acc[type] = (acc[type] || 0) + 1;
            return acc;
        }, {});
        
        return Object.entries(typeCounts).map(([name, value]) => ({
            name,
            value,
            percentage: (value / absences.length * 100).toFixed(1)
        }));
    };

    const chartData = processData();

    return (
        <Paper elevation={3} sx={{ p: 2, height: '100%' }}>
            <Typography variant="h6" gutterBottom>
                Absence Distribution
            </Typography>
            {chartData.length > 0 ? (
                <ResponsiveContainer width="100%" height={300}>
                    <PieChart>
                        <Pie
                            data={chartData}
                            cx="50%"
                            cy="50%"
                            labelLine={false}
                            outerRadius={80}
                            fill="#8884d8"
                            dataKey="value"
                            label={({ name, percentage }) => `${name}: ${percentage}%`}
                        >
                            {chartData.map((entry, index) => (
                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                            ))}
                        </Pie>
                        <Tooltip 
                            formatter={(value, name, props) => [
                                `${value} (${props.payload.percentage}%)`, 
                                name
                            ]}
                        />
                        <Legend />
                    </PieChart>
                </ResponsiveContainer>
            ) : (
                <Typography variant="body2" color="text.secondary">
                    No absence data available
                </Typography>
            )}
        </Paper>
    );
};

export default AbsenceTypeChart;