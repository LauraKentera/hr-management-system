import React from 'react';

function DashboardView() {
    const employeeCount = 100;
    const avgSalary = 50000;
    const kpis = {
        kpi1: 75,
        kpi2: 88,
        kpi3: 92,
    };

    return (
        <div className="dashboard">
            <h2>Dashboard</h2>
            <div>
                <p>Employee Count: {employeeCount}</p>
                <p>Average Salary: ${avgSalary}</p>
                <div>
                    <h3>KPIs</h3>
                    <p>KPI 1: {kpis.kpi1}%</p>
                    <p>KPI 2: {kpis.kpi2}%</p>
                    <p>KPI 3: {kpis.kpi3}%</p>
                </div>
            </div>
        </div>
    );
}

export default DashboardView;
