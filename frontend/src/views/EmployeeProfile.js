import React from 'react';
import { useParams } from 'react-router-dom';
import EmployeeBenefitManager from '../components/EmployeeBenefitManager';
// import other details about the employee

function EmployeeProfile() {
    const { id } = useParams(); // employee ID from route

    return (
        <div className="container mt-4">
            <h2>Employee Profile</h2>

            {/* Other employee details here... */}

            <EmployeeBenefitManager employeeId={parseInt(id)} />
        </div>
    );
}

export default EmployeeProfile;
