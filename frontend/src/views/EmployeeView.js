import React, { useState, useEffect } from 'react';
import EmployeeTable from '../components/EmployeeTable';
import EmployeeForm from '../components/EmployeeForm';
import departmentService from '../services/departmentService';

const EmployeeView = () => {
  const [openForm, setOpenForm] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [departments, setDepartments] = useState([]);

  const fetchDepartments = async () => {
    const res = await departmentService.getDepartments();
    setDepartments(res.data);
  };

  useEffect(() => { fetchDepartments(); }, []);

  const handleEdit = (employee) => {
    setSelectedEmployee(employee);
    setOpenForm(true);
  };

  const handleCloseForm = () => {
    setOpenForm(false);
    setSelectedEmployee(null);
  };

  return (
    <>
      <EmployeeTable onEdit={handleEdit} />
      <EmployeeForm
        open={openForm}
        onClose={handleCloseForm}
        onSave={() => window.location.reload()} // or refetch
        initialData={selectedEmployee}
        departments={departments}
      />
    </>
  );
};

export default EmployeeView;
