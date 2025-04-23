// src/services/employeeService.js
import axios from 'axios';

const BASE_URL = '/api/employees';

const getEmployees = () => axios.get(BASE_URL);
const deleteEmployee = (id) => axios.delete(`${BASE_URL}/${id}`);
const createEmployee = (data) => axios.post(BASE_URL, data);
const updateEmployee = (id, data) => axios.put(`${BASE_URL}/${id}`, data);

export default {
    getEmployees,
    deleteEmployee,
    createEmployee,
    updateEmployee,
  };