// src/services/departmentService.js
import axios from 'axios';

const BASE_URL = '/api/departments';

const getDepartments = () => axios.get(BASE_URL);
const createDepartment = (data) => axios.post(BASE_URL, data);
const updateDepartment = (id, data) => axios.put(`${BASE_URL}/${id}`, data);

export default {
  getDepartments,
  createDepartment,
  updateDepartment,
};
